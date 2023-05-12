package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.carefirstpraxis.carefirst_messenger_android.wearable.R

/**
 * Simple Chip for displaying the Watch models.
 */
@Composable
fun WatchAppChip(
  watchModelNumber: Int,
  watchName: String,
  watchIcon: Int,
  onClickWatch: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Chip(
    modifier = modifier.fillMaxWidth(),
    icon = {
      Icon(
        painter = painterResource(id = watchIcon),
        contentDescription = stringResource(R.string.watch_icon_content_description),
        modifier = Modifier.size(24.dp).wrapContentSize(align = Alignment.Center)
      )
    },
    label = {
      Text(
        text = watchName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    secondaryLabel = {
      Text(
        text = "id: $watchModelNumber",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    onClick = {
      onClickWatch(watchModelNumber)
    }
  )
}

@Preview(
  apiLevel = 26,
  uiMode = Configuration.UI_MODE_TYPE_WATCH
)
@Composable
fun PreviewWatchAppChip() {
  Box {
    WatchAppChip(
      watchModelNumber = 123456,
      watchName = "Watch 123456 Name",
      watchIcon = R.drawable.ic_watch,
      onClickWatch = { }
    )
  }
}
