package com.carefirstpraxis.carefirst_messenger_android.wearable.data

import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Simple message repository
 */
class MessageRepository(
  private val messagesLocalDataSource: MessagesLocalDataSource
) {
  val watches: List<MessageModel> = messagesLocalDataSource.watches

  fun getWatch(id: Int): MessageModel? {
    return messagesLocalDataSource.watches.firstOrNull {
      it.messageId == id
    }
  }

  companion object {
    val MESSAGE_REPOSITORY_KEY = object : CreationExtras.Key<MessageRepository> {}
  }
}
