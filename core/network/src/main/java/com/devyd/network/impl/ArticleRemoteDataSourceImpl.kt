package com.devyd.network.impl

import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.models.NewsDto
import com.devyd.network.models.toEntity
import com.devyd.network.retrofit.RetrofitClient
import java.io.IOException

class ArticleRemoteDataSourceImpl : ArticleRemoteDataSource {
    override suspend fun fetchNewsEntity(): NewsDto {
        val response = RetrofitClient.api.getBusinessHeadlines()

        if (!response.isSuccessful) {
            LogUtil.e(logTag(), "${response.code()}")
            throw IOException("Unexpected code $response")
        }

        if (response.body() == null) {
            throw IOException("response.body() is null")
        }

        val result = response.body().toString()
        LogUtil.i(logTag(), "${response.code()}")
        LogUtil.i(logTag(), result)
        // 데이터 변환 Json -> ArticleDto

        return response.body()!!.toEntity()
    }
}