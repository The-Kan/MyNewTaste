package com.devyd.network.impl

import android.util.Log
import com.devyd.common.util.LogUtil
import com.devyd.common.util.logTag
import com.devyd.data.datasource.ArticleRemoteDataSource
import com.devyd.data.models.ArticleDto
import com.devyd.network.retrofit.ArticleService
import java.io.IOException
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(
    private val service: ArticleService
) : ArticleRemoteDataSource {
    override suspend fun fetchArticles(): List<ArticleDto> {
        val response = service.getPostSuspend(1)

        if (!response.isSuccessful) {
            LogUtil.e(logTag(), "${response.code()}")
            throw IOException("Unexpected code $response")
        }
        val result = response.body().toString()
        LogUtil.i(logTag(), "${response.code()}")
        LogUtil.i(logTag(), result)
        // 데이터 변환 Json -> ArticleDto

        return emptyList()
    }
}