package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watchlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import com.carefirstpraxis.carefirst_messenger_android.wearable.R
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.WatchModel
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.components.WatchAppChip
import com.google.android.horologist.compose.navscaffold.scrollableColumn

@Composable
fun WatchListScreen(
  viewModel: WatchListViewModel,
  scalingLazyListState: ScalingLazyListState,
  focusRequester: FocusRequester,
  showVignette: Boolean,
  onClickVignetteToggle: (Boolean) -> Unit,
  onClickWatch: (Int) -> Unit
) {
  val watches by viewModel.watches
  WatchListScreen(
    watches = watches,
    scalingLazyListState = scalingLazyListState,
    focusRequester = focusRequester,
    showVignette = showVignette,
    onClickVignetteToggle = onClickVignetteToggle,
    onClickWatch = onClickWatch
  )
}

/**
 * Displays a list of messages plus a [ToggleChip] at the top to display/hide the Vignette around
 * the screen. The list is powered using a [ScalingLazyColumn].
 */
@Composable
fun WatchListScreen(
  watches: List<WatchModel>,
  scalingLazyListState: ScalingLazyListState,
  focusRequester: FocusRequester,
  showVignette: Boolean,
  onClickVignetteToggle: (Boolean) -> Unit,
  onClickWatch: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  ScalingLazyColumn(
    modifier = modifier.scrollableColumn(focusRequester, scalingLazyListState),
    state = scalingLazyListState
  ) {
    item {
      ToggleChip(
        modifier = Modifier.height(48.dp)
         .padding(horizontal = if (LocalConfiguration.current.isScreenRound) {
           20.dp
         }
         else {
           10.dp
         },
         vertical = 8.dp
       ),
       checked = showVignette,
       onCheckedChange = onClickVignetteToggle,
       label = {
         Text(
           text = stringResource(R.string.vignette_toggle_chip_label),
           textAlign = TextAlign.Center,
           fontSize = 12.sp,
           modifier = Modifier.fillMaxSize()
         )
       },
       toggleControl = {
                    Switch(
                        checked = showVignette,
                        modifier = Modifier.semantics {
                            this.contentDescription = if (showVignette) "On" else "Off"
                        }
                    )
                }
            )
        }

        // Displays all watches.
        items(watches) { watch ->
            WatchAppChip(
                messageId = watch.messageId,
                subject = watch.subject,
                watchName = watch.name,
                watchIcon = watch.icon,
                onClickWatch = onClickWatch
            )
        }
    }
}
