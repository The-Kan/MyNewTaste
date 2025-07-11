package com.devyd.di

import android.content.Context
import androidx.room.Room
import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.datasource.CategoryWeightLocalDataSource
import com.devyd.data.repositoryimpl.ArticleRepositoryImpl
import com.devyd.data.repositoryimpl.categoryweight.DBCategoryWeightRepository
import com.devyd.domain.repository.ArticleRepository
import com.devyd.domain.repository.CategoryWeightRepository
import com.devyd.local.dao.CategoryWeightDao
import com.devyd.local.database.CategoryWeightDatabase
import com.devyd.local.impl.RoomCategoryWeightLocalDataSource
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
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository =
        ArticleRepositoryImpl(articleRemoteDataSource)

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(): ArticleRemoteDataSource =
        ArticleRemoteDataSourceImpl()

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
    fun provideDatabase(
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
}
