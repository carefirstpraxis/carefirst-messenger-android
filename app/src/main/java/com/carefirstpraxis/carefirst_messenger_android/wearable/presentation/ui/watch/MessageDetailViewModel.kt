package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.MessageModel
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.MessageRepository
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.BaseApplication

/**
 * ViewModel for the Watch Detail Screen (only needs watch id).
 */
class MessageDetailViewModel(
    watchId: Int,
    messageRepository: MessageRepository
) : ViewModel() {
  private val _watch: MutableState<MessageModel?> = mutableStateOf(messageRepository.getWatch(watchId))
  val watch: State<MessageModel?>
  get() = _watch

    companion object {
      fun factory(watchId: Int) = viewModelFactory {
        initializer {
          val baseApplication = this[APPLICATION_KEY] as BaseApplication
          MessageDetailViewModel(
            watchId = watchId,
            messageRepository = baseApplication.messageRepository
          )
        }
      }
    }
}
