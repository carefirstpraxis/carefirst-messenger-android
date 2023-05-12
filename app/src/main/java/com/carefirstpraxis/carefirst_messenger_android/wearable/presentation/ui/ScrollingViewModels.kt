package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui

import androidx.compose.foundation.ScrollState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import com.carefirstpraxis.carefirst_messenger_android.wearable.util.saveable

const val SCROLL_STATE_KEY = "scrollState"

// Saves scroll state through process death; inspired by https://issuetracker.google.com/195689777
class ScalingLazyListStateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
  val scrollState = savedStateHandle.saveable(
    key = SCROLL_STATE_KEY,
    saver = ScalingLazyListState.Saver
  ) {
      ScalingLazyListState()
  }
}

class ScrollStateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
  val scrollState = savedStateHandle.saveable(
    key = SCROLL_STATE_KEY,
      saver = ScrollState.Saver
  ) {
    ScrollState(0)
  }
}
