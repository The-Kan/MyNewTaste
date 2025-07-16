package com.devyd.common

object Constants {
    const val ARTICLE_CLICK = "ARTICLE_CLICK"
    const val ARTICLE = "ARTICLE"
    const val CATEGORY_SETTING_GUIDE_CLICK = "CATEGORY_SETTING_GUIDE_CLICK"

    val NEWS_API_TOP_HEADLINES_CATEGORY_LIST = listOf(
        R.string.category_1,
        R.string.category_2,
        R.string.category_3,
        R.string.category_4,
        R.string.category_5,
        R.string.category_6,
        R.string.category_7
    )
}

object CategoryStrings {
    const val BUSINESS = "business"
    const val ENTERTAINMENT = "entertainment"
    const val GENERAL = "general"
    const val HEALTH = "health"
    const val SCIENCE = "science"
    const val SPORTS = "sports"
    const val TECHNOLOGY = "technology"
    const val UNKNOWN = "unknown"

    val validValues = listOf(
        BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY
    )

    fun contains(value: String): Boolean {
        return value in validValues
    }
}