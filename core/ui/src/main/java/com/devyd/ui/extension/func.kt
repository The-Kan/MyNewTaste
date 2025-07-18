package com.devyd.ui.extension

import android.content.Context
import com.devyd.common.CategoryStrings
import com.devyd.common.Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST

fun Context.getCategorySystemName(categoryEnglishName: String): String {
    val categoryPosition = CategoryStrings.validValues.indexOf(categoryEnglishName)
    if (categoryPosition == -1 || categoryPosition >= NEWS_API_TOP_HEADLINES_CATEGORY_LIST.size) {
        throw IllegalArgumentException("Invalid category: $categoryEnglishName")
    }
    return getString(NEWS_API_TOP_HEADLINES_CATEGORY_LIST[categoryPosition])
}