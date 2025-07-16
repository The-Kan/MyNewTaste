package com.devyd.di

import android.content.Context
import androidx.room.Room
import com.devyd.data.datasource.ArticleLocalDataSource
import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.datasource.CategoryWeightLocalDataSource
import com.devyd.data.repositoryimpl.ArticleRepositoryImpl
import com.devyd.data.repositoryimpl.categoryweight.DBCategoryWeightRepository
import com.devyd.domain.repository.ArticleRepository
import com.devyd.domain.repository.CategoryWeightRepository
import com.devyd.local.article.dao.ArticlesDao
import com.devyd.local.article.database.ArticleDatabase
import com.devyd.local.article.impl.RoomArticleLocalDataSource
import com.devyd.local.categoryweight.dao.CategoryWeightDao
import com.devyd.local.categoryweight.database.CategoryWeightDatabase
import com.devyd.local.categoryweight.impl.RoomCategoryWeightLocalDataSource
import com.devyd.network.impl.ArticleRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    @Singleton
    fun provideArticleRepository(
        articleRemoteDataSource: ArticleRemoteDataSource,
        articleLocalDataSource: ArticleLocalDataSource
    ): ArticleRepository =
        ArticleRepositoryImpl(articleRemoteDataSource, articleLocalDataSource)

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(): ArticleRemoteDataSource =
        ArticleRemoteDataSourceImpl()

    @Provides
    @Singleton
    fun provideArticleLocalDataSource(articlesDao: ArticlesDao): ArticleLocalDataSource =
        RoomArticleLocalDataSource(articlesDao)

//    @Provides
//    @Singleton
//    fun provideCategoryWeightRepository() : CategoryWeightRepository =
//        InMemoryCategoryWeightRepository()

    @Provides
    @Singleton
    fun provideCategoryWeightRepository(categoryWeightLocalDataSource: CategoryWeightLocalDataSource): CategoryWeightRepository =
        DBCategoryWeightRepository(categoryWeightLocalDataSource)

    @Provides
    @Singleton
    fun provideCategoryWeightLocalDataSource(categoryWeightDao: CategoryWeightDao): CategoryWeightLocalDataSource =
        RoomCategoryWeightLocalDataSource(categoryWeightDao)

    @Provides
    @Singleton
    fun provideCategoryWeightDatabase(
        @ApplicationContext context: Context
    ): CategoryWeightDatabase =
        Room.databaseBuilder(
            context,
            CategoryWeightDatabase::class.java,
            "category_weight_db"
        ).build()

    @Provides
    fun provideCategoryWeightDao(
        db: CategoryWeightDatabase
    ): CategoryWeightDao =
        db.categoryWeightDao()

    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "article_db"
        ).build()
    }

    @Provides
    fun provideArticlesDao(database: ArticleDatabase): ArticlesDao {
        return database.articlesDao()
    }
}
