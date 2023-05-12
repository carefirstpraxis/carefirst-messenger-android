package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watch

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.carefirstpraxis.carefirst_messenger_android.wearable.R
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.MessageModel
import com.google.android.horologist.compose.navscaffold.scrollableColumn

@Composable
fun MessageDetailScreen(
    viewModel: MessageDetailViewModel,
    scrollState: ScrollState,
    focusRequester: FocusRequester
) {
  val watch by viewModel.watch
  MessageDetailScreen(
    watch = watch,
    scrollState = scrollState,
    focusRequester = focusRequester
  )
}

/**
 * Displays the icon, title, and description of the watch model.
 */
@Composable
fun MessageDetailScreen(
  watch: MessageModel?,
  scrollState: ScrollState,
  focusRequester: FocusRequester,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .scrollableColumn(focusRequester, scrollState)
      .verticalScroll(scrollState)
      .padding(
        top = 26.dp,
        start = 8.dp,
        end = 8.dp,
        bottom = 26.dp
      ),
      verticalArrangement = Arrangement.Top
    ) {
      if (watch == null) {
        Text(
          modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally),
          color = Color.White,
          textAlign = TextAlign.Center,
          text = stringResource(R.string.invalid_watch_label)
        )
      } else {
        Icon(
          painter = painterResource(id = watch.icon),
          tint = MaterialTheme.colors.primary,
          contentDescription = stringResource(R.string.watch_icon_content_description),
          modifier = Modifier
           .fillMaxWidth()
           .size(24.dp)
           .wrapContentSize(align = Alignment.Center)
          )

          Spacer(modifier = Modifier.size(4.dp))

          Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            text = watch.name
          )
          Spacer(modifier = Modifier.size(4.dp))

          Text(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            color = Color.White,
            textAlign = TextAlign.Center,
            text = watch.description
          )
        }
    }
}
