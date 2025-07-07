package com.devyd.di

import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.repositoryimpl.ArticleRepositoryImpl
import com.devyd.domain.repository.ArticleRepository
import com.devyd.network.impl.ArticleRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    @Singleton
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository =
        ArticleRepositoryImpl(articleRemoteDataSource)

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(): ArticleRemoteDataSource =
        ArticleRemoteDataSourceImpl()
}
