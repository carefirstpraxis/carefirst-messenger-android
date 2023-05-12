package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation

import android.app.Application
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.MessagesLocalDataSource
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.MessageRepository

/**
 * Provides repository for watch devices.
 */
class BaseApplication : Application() {
  private val messagesLocalDataSource by lazy {
    MessagesLocalDataSource()
  }

  val messageRepository by lazy {
    MessageRepository(messagesLocalDataSource = messagesLocalDataSource)
  }
}
