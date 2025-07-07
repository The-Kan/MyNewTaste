package com.devyd.di

import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.repositoryimpl.ArticleRepositoryImpl
import com.devyd.domain.repository.ArticleRepository
import com.devyd.network.impl.ArticleRemoteDataSourceImpl
import com.devyd.network.retrofit.ArticleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideArticleRemoteDataSource(service: ArticleService): ArticleRemoteDataSource =
        ArticleRemoteDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideArticleService(retrofit: Retrofit): ArticleService =
        retrofit.create(ArticleService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
