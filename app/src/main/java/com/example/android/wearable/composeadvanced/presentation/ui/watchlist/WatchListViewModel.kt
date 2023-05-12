package com.example.android.wearable.composeadvanced.presentation.ui.watchlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android.wearable.composeadvanced.data.WatchModel
import com.example.android.wearable.composeadvanced.data.WatchRepository
import com.example.android.wearable.composeadvanced.presentation.BaseApplication

/**
 * ViewModel for the Watch List Screen.
 */
class WatchListViewModel(watchRepository: WatchRepository) : ViewModel() {
  private val _watches: MutableState<List<WatchModel>> = mutableStateOf(watchRepository.watches)
  val watches: State<List<WatchModel>>
  get() = _watches

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val baseApplication =
        this[APPLICATION_KEY] as BaseApplication
      WatchListViewModel(
        watchRepository = baseApplication.watchRepository
      )
    }
  }
 }
}
