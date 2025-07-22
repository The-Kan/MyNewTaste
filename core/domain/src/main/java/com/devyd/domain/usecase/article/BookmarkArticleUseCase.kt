package com.devyd.domain.usecase.article

import com.devyd.domain.models.Article
import com.devyd.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class BookmarkArticleUseCase @Inject constructor(
    private val bookmarkedArticleRepository: BookmarkedArticleRepository
) {
    suspend operator fun invoke(article: Article): Long {
        return bookmarkedArticleRepository.bookmark(article)
    }
}