package com.leaning.against.mountains.drinkingwater.utils

import androidx.annotation.Keep


@Keep
data class HistoryBean(
    val date: String,
    val totalDrink: Int,
    val cupCount: Int,
    val progress: Int
)
