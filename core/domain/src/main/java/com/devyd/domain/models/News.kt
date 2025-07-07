package com.devyd.domain.models

data class News(
    val status: String = "ok",
    val totalResults: Int = 1,
    val articles: List<Article> = listOf(Article())
)

data class Article(
    val source: Source = Source(),
    val author: String? = "Louis Ramirez",
    val title: String = "Amazon Prime Day deals LIV",
    val description: String? = "Epic Prime Day deals are here",
    val url: String = "https://www.tomsguide.com/live/news/best-prime-day-deals-2025",
    val urlToImage: String? = "https://cdn.mos.cms.futurecdn.net/jUAXGe2oNTqPNGkpHtCt8c.jpg",
    val publishedAt: String = "2025-07-06T13:40:21Z",
    val content: String? = "2025-07-06T14:41:04.838ZLet me see your grill\\r\\n(Image credit: Getty Images)\\r\\nAre you looking to get a grill to make some of that delicious summer food like burgers, hot dogs and more? Check out the d… [+3464 chars]"
)

data class Source(
    val id: String? = null,
    val name: String = "Tom"
)