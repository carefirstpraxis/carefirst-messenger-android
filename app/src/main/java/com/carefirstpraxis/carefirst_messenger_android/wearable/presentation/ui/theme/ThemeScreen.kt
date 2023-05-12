package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.RadioButton
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.theme.ThemeValues
import com.google.android.horologist.compose.navscaffold.scrollableColumn

@Composable
internal fun ThemeScreen(
  scalingLazyListState: ScalingLazyListState,
  focusRequester: FocusRequester,
  currentlySelectedColors: Colors,
  availableThemes: List<ThemeValues>,
  onValueChange: (Colors) -> Unit
) {
  ScalingLazyColumn(
    modifier = Modifier.scrollableColumn(focusRequester, scalingLazyListState),
    state = scalingLazyListState
  ) {
    item {
      ListHeader {
        Text("Color Schemes")
      }
    }
    for (listItem in availableThemes) {
      val checked = listItem.colors == currentlySelectedColors
        item {
          ToggleChip(
            checked = checked,
            toggleControl = {
              RadioButton(
                selected = checked,
                modifier = Modifier.semantics {
                  this.contentDescription = if (checked) "On" else "Off"
                }
              )
            },
            onCheckedChange = { onValueChange(listItem.colors) },
              // Override the default toggle control color to show the user the current
              // primary selected color.
              colors = ToggleChipDefaults.toggleChipColors(
              checkedToggleControlColor = currentlySelectedColors.primary
            ),
            label = {
              Text(listItem.description)
            },
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }
}
