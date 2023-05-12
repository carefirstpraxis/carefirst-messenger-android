package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.userinput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.InlineSliderDefaults

/**
 * Displays a Slider, which allows users to make a selection from a range of values.
 */
@Composable
fun SliderScreen(
  displayValue: Int,
  onValueChange: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(top = 10.dp),
    verticalArrangement = Arrangement.Center
  ) {
    InlineSlider(
      value = displayValue,
      onValueChange = onValueChange,
      valueProgression = 1..10,
      increaseIcon = { Icon(InlineSliderDefaults.Increase, "Increase") },
      decreaseIcon = { Icon(InlineSliderDefaults.Decrease, "Decrease") }
    )
  }
}
