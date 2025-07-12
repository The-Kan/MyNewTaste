package com.devyd.domain.usecase

import com.devyd.domain.models.Articles
import com.devyd.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(): Articles = repository.getNews()
}