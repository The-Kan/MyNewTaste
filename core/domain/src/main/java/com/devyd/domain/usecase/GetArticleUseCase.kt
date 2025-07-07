package com.devyd.domain.usecase

import com.devyd.domain.models.News
import com.devyd.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(): News = repository.getNews()
}