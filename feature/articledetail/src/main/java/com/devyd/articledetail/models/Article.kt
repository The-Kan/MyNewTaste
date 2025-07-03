package com.devyd.articledetail.models

import androidx.annotation.DrawableRes

data class Article(
    val id: Int,
    val title: String,
    @DrawableRes val imageRes: Int,
    val content: String
)