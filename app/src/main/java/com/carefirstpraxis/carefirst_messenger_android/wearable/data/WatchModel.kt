package com.carefirstpraxis.carefirst_messenger_android.wearable.data

import com.carefirstpraxis.carefirst_messenger_android.wearable.R

/**
 * Simple Model representing fake watch models.
 */
data class WatchModel(
  val modelId: Int,
  val name: String,
  val description: String,
  val icon: Int = R.drawable.ic_watch
)
