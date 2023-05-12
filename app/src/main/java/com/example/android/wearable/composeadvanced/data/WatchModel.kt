package com.example.android.wearable.composeadvanced.data

import com.example.android.wearable.composeadvanced.R

/**
 * Simple Model representing fake watch models.
 */
data class WatchModel(
  val modelId: Int,
  val name: String,
  val description: String,
  val icon: Int = R.drawable.ic_watch
)
