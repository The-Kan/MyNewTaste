package com.devyd.domain.usecase.article

import com.devyd.domain.models.Articles
import com.devyd.domain.repository.BookmarkedArticleRepository
import javax.inject.Inject

class GetBookmarkArticleUseCase @Inject constructor(
    private val bookmarkedArticleRepository: BookmarkedArticleRepository
) {
    suspend operator fun invoke(): Articles = bookmarkedArticleRepository.getArticles()
}