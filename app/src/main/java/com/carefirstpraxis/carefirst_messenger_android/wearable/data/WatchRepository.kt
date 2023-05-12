package com.carefirstpraxis.carefirst_messenger_android.wearable.data

import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Simple Watch repository for fake watch models.
 */
class WatchRepository(
  private val watchLocalDataSource: WatchLocalDataSource
) {
  val watches: List<WatchModel> = watchLocalDataSource.watches

  fun getWatch(id: Int): WatchModel? {
    return watchLocalDataSource.watches.firstOrNull {
      it.modelId == id
    }
  }

  companion object {
    val WATCH_REPOSITORY_KEY = object : CreationExtras.Key<WatchRepository> {}
  }
}
