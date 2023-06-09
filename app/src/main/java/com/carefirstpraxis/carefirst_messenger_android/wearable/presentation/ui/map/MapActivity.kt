package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.theme.WearAppTheme

/**
 * Compose for Wear OS activity that demonstrates a Map.
 *
 * Needs to be in a separate activity because of scroll interaction issues inside
 * SwipeDismissableNavHost.
 */
class MapActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

      setContent {
        WearAppTheme {
          MapScreen(
            onClose = {
              finish()
            }
          )
        }
      }
    }
}
