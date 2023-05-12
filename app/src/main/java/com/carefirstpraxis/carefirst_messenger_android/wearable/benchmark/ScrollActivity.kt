package com.carefirstpraxis.carefirst_messenger_android.wearable.benchmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.carefirstpraxis.carefirst_messenger_android.wearable.util.JankPrinter

/**
 * Compose for Wear OS app that demonstrates how to use Wear specific Scaffold, Navigation,
 * curved text, Chips, and many other composables.
 *
 * Displays different text at the bottom of the landing screen depending on shape of the device
 * (round vs. square/rectangular).
 */
class ScrollActivity : ComponentActivity() {
  private lateinit var jankPrinter: JankPrinter
  internal lateinit var navController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    jankPrinter = JankPrinter()

    setContent {
      navController = rememberSwipeDismissableNavController()
      SomeScreenContent()
      LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
          jankPrinter.setRouteState(route = it.destination.route)
        }
      }
    }
    jankPrinter.installJankStats(activity = this)
  }
}

@Composable
fun SomeScreenContent() {
  val checked = remember { mutableStateMapOf<Int, Boolean>() }

  ScalingLazyColumn(
    Modifier
      .fillMaxSize()
      .semantics {
        contentDescription = "ScalingLazyColumn"
      }
  ) {
    items(20) { item ->
      val itemChecked = checked.getOrDefault(item, false)
      ToggleChip(
        checked = itemChecked,
        onCheckedChange = { isChecked ->
          checked[item] = isChecked
        },
        label = {
          Text("Item $item")
        },
        toggleControl = {
          Switch(
            checked = itemChecked,
            modifier = Modifier.semantics {
              this.contentDescription = if (itemChecked) "Checked" else "Unchecked"
            }
          )
       }
     )
     }
  }
}
