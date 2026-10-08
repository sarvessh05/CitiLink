package com.citilink.citizen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.citilink.citizen.data.NashikTransitRepository
import com.citilink.citizen.data.TransitHub
import com.citilink.citizen.ui.components.AuthModal
import com.citilink.citizen.ui.components.CitizenNavTab
import com.citilink.citizen.ui.components.CitiLinkScaffold
import com.citilink.citizen.ui.components.HubSwitcherModal
import com.citilink.citizen.ui.components.UserAuthProfile
import com.citilink.citizen.ui.screens.ActivePinScreen
import com.citilink.citizen.ui.screens.BookedTicketResult
import com.citilink.citizen.ui.screens.BookingScreen
import com.citilink.citizen.ui.screens.CorridorRadarScreen
import com.citilink.citizen.ui.screens.HomeScreen
import com.citilink.citizen.ui.screens.ProfileScreen
import com.citilink.citizen.ui.theme.CitiLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            CitiLinkTheme(darkTheme = isDarkMode) {
                CitiLinkMainApp(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}

@Composable
fun CitiLinkMainApp(
    isDarkMode: Boolean = false,
    onToggleDarkMode: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(CitizenNavTab.HOME) }
    var selectedHub by remember { mutableStateOf(NashikTransitRepository.HUB_CBS) }
    var showHubSwitcher by remember { mutableStateOf(false) }
    var showAuthModal by remember { mutableStateOf(false) }
    var userProfile by remember { mutableStateOf(UserAuthProfile()) }

    var activeTravelPin by remember { mutableStateOf<String?>("142") }
    var activeTicketDetails by remember {
        mutableStateOf(
            BookedTicketResult(
                pin = "142",
                routeNumber = "Route 12",
                fromStop = "CBS West Stand 3",
                toStop = "Panchavati Karanja",
                passengerCount = 1,
                isSeniorCitizen = false,
                farePaid = 20,
                paymentMode = "UPI / GPay"
            )
        )
    }
    var showBookingScreen by remember { mutableStateOf(false) }

    if (showBookingScreen) {
        BookingScreen(
            onBackClick = { showBookingScreen = false },
            onBookingSuccess = { result ->
                activeTicketDetails = result
                activeTravelPin = result.pin
                showBookingScreen = false
                selectedTab = CitizenNavTab.ACTIVE_PIN
            }
        )
    } else {
        CitiLinkScaffold(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            cityName = "Nashik • ${selectedHub.standLabel.substringBefore(" ")}",
            screenSubtitle = when (selectedTab) {
                CitizenNavTab.HOME -> "${selectedHub.name} • Live"
                CitizenNavTab.CORRIDOR -> "Route 12 Corridor Radar"
                CitizenNavTab.ACTIVE_PIN -> "Active Travel PIN"
                CitizenNavTab.PROFILE -> "Passenger Profile"
            },
            isDarkMode = isDarkMode,
            activePinCount = if (activeTravelPin != null) 1 else 0,
            onCityClick = { showHubSwitcher = true },
            onToggleDarkMode = onToggleDarkMode,
            onProfileClick = { showAuthModal = true }
        ) {
            when (selectedTab) {
                CitizenNavTab.HOME -> {
                    HomeScreen(
                        selectedHub = selectedHub,
                        onHubSelected = { selectedHub = it },
                        onNavigateToCorridor = { selectedTab = CitizenNavTab.CORRIDOR },
                        onNavigateToActivePin = { selectedTab = CitizenNavTab.ACTIVE_PIN },
                        onBookBus = { bus ->
                            showBookingScreen = true
                        },
                        activeTravelPin = activeTravelPin
                    )
                }
                CitizenNavTab.CORRIDOR -> {
                    CorridorRadarScreen(
                        onBackClick = { selectedTab = CitizenNavTab.HOME },
                        onBookRouteClick = {
                            showBookingScreen = true
                        }
                    )
                }
                CitizenNavTab.ACTIVE_PIN -> {
                    ActivePinScreen(ticket = activeTicketDetails)
                }
                CitizenNavTab.PROFILE -> {
                    ProfileScreen(
                        onRebookRoute = { routeName ->
                            selectedTab = CitizenNavTab.CORRIDOR
                        }
                    )
                }
            }
        }
    }

    // Global Hub Switcher Modal (Accessible from any screen via Top Bar or Home Stand pill)
    if (showHubSwitcher) {
        HubSwitcherModal(
            selectedHub = selectedHub,
            onHubSelected = {
                selectedHub = it
                showHubSwitcher = false
            },
            onDismissRequest = { showHubSwitcher = false }
        )
    }

    // Global Citizen Auth & Registration Modal
    if (showAuthModal) {
        AuthModal(
            currentProfile = userProfile,
            onProfileUpdated = {
                userProfile = it
                showAuthModal = false
            },
            onDismissRequest = { showAuthModal = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CitiLinkAppPreview() {
    CitiLinkTheme {
        CitiLinkMainApp()
    }
}