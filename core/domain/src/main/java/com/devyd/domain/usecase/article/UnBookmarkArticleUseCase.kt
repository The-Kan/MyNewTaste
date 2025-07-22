package com.devyd.domain.usecase.article

import com.devyd.domain.models.Article
import com.devyd.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class UnBookmarkArticleUseCase @Inject constructor(
    private val bookmarkedArticleRepository: BookmarkedArticleRepository
) {
    suspend operator fun invoke(article: Article) = bookmarkedArticleRepository.unBookmark(article)
}