package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.progressindicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.Text
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.MenuItem
import com.google.android.horologist.compose.navscaffold.scrollableColumn

@Composable
fun ProgressIndicatorsScreen(
  scalingLazyListState: ScalingLazyListState,
  focusRequester: FocusRequester,
  menuItems: List<MenuItem>,
  modifier: Modifier = Modifier
) {
  ScalingLazyColumn(
    modifier = modifier
      .scrollableColumn(focusRequester, scalingLazyListState)
      .fillMaxWidth(),
    state = scalingLazyListState,
    anchorType = ScalingLazyListAnchorType.ItemStart
    ) {
    for (menuItem in menuItems) {
      item {
        CompactChip(
          onClick = menuItem.clickHander,
          shape = CutCornerShape(4.dp),
          label = {
            Text(
              menuItem.name,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          },
          modifier = Modifier.wrapContentWidth()
        )
      }
    }
  }
}

@Composable
fun IndeterminateProgressIndicator(
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
  }
}

@Composable
fun FullScreenProgressIndicator(
  modifier: Modifier = Modifier
) {
  val transition = rememberInfiniteTransition()

  val currentRotation by transition.animateFloat(
    0f,
    1f,
    infiniteRepeatable(
      animation = tween(
        durationMillis = 5000,
        easing = LinearEasing,
        delayMillis = 1000
      )
    )
  )
  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    CircularProgressIndicator(
      startAngle = 315f,
      endAngle = 225f,
      progress = currentRotation,
      modifier = Modifier.fillMaxSize().padding(all = 1.dp)
    )
  }
}
