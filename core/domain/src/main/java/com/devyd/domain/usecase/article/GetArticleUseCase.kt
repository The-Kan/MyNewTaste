package com.devyd.domain.usecase.article

import com.devyd.domain.models.Articles
import com.devyd.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(category: String): Articles = repository.getArticles(category)
}