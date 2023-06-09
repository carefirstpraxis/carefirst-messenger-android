package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watchlist

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
 * ViewModel for the Watch List Screen.
 */
class MessageListViewModel(messageRepository: MessageRepository) : ViewModel() {
  private val _watches: MutableState<List<MessageModel>> = mutableStateOf(messageRepository.watches)
  val watches: State<List<MessageModel>>
  get() = _watches

  companion object {
    val Factory = viewModelFactory {
      initializer {
        val baseApplication =
        this[APPLICATION_KEY] as BaseApplication
      MessageListViewModel(
        messageRepository = baseApplication.messageRepository
      )
    }
  }
 }
}
