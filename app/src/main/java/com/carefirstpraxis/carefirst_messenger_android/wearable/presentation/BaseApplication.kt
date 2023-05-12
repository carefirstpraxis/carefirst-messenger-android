package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation

import android.app.Application
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.WatchLocalDataSource
import com.carefirstpraxis.carefirst_messenger_android.wearable.data.WatchRepository

/**
 * Provides repository for watch devices.
 */
class BaseApplication : Application() {
  private val watchLocalDataSource by lazy {
    WatchLocalDataSource()
  }

  val watchRepository by lazy {
    WatchRepository(watchLocalDataSource = watchLocalDataSource)
  }
}
