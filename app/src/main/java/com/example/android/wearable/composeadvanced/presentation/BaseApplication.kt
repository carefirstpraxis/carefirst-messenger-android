package com.example.android.wearable.composeadvanced.presentation

import android.app.Application
import com.example.android.wearable.composeadvanced.data.WatchLocalDataSource
import com.example.android.wearable.composeadvanced.data.WatchRepository

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
