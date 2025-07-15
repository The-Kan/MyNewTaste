package com.devyd.common.models

import com.devyd.domain.models.CategoryWeight

sealed class CategoryWeightResult {
    object Idle : CategoryWeightResult()
    object Loading : CategoryWeightResult()
    data class Success(val categoryWeightList: List<CategoryWeight>) : CategoryWeightResult()
    data class Failure(val error : String) : CategoryWeightResult()
}