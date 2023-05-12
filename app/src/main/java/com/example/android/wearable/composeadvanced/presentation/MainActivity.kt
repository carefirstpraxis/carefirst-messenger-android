package com.example.android.wearable.composeadvanced.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.android.wearable.composeadvanced.data.WatchRepository
import com.example.android.wearable.composeadvanced.util.JankPrinter

/**
 * Compose for Wear OS app that demonstrates how to use Wear specific Scaffold, Navigation,
 * curved text, Chips, and many other composables.
 *
 * Displays different text at the bottom of the landing screen depending on shape of the device
 * (round vs. square/rectangular).
 */
class MainActivity : ComponentActivity() {
  private lateinit var jankPrinter: JankPrinter
  internal lateinit var navController: NavHostController


  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    jankPrinter = JankPrinter()
    setTheme(android.R.style.Theme_DeviceDefault)

    setContent {
      navController = rememberSwipeDismissableNavController()
      WearApp(swipeDismissableNavController = navController)
      LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
          jankPrinter.setRouteState(route = it.destination.route)
        }
      }
    }
    jankPrinter.installJankStats(activity = this)
 }

  override val defaultViewModelCreationExtras: CreationExtras
    get() = MutableCreationExtras(super.defaultViewModelCreationExtras).apply {
      set(WatchRepository.WATCH_REPOSITORY_KEY, (application as BaseApplication).watchRepository)
    }
}
