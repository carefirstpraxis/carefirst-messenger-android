package com.example.android.wearable.composeadvanced.presentation.ui.watch

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
 * ViewModel for the Watch Detail Screen (only needs watch id).
 */
class WatchDetailViewModel(
  watchId: Int,
  watchRepository: WatchRepository
) : ViewModel() {
  private val _watch: MutableState<WatchModel?> = mutableStateOf(watchRepository.getWatch(watchId))
  val watch: State<WatchModel?>
  get() = _watch

    companion object {
      fun factory(watchId: Int) = viewModelFactory {
        initializer {
          val baseApplication = this[APPLICATION_KEY] as BaseApplication
          WatchDetailViewModel(
            watchId = watchId,
            watchRepository = baseApplication.watchRepository
          )
        }
      }
    }
}
