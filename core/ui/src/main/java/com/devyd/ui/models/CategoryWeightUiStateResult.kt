package com.devyd.ui.models


sealed class CategoryWeightUiStateResult {
    object Idle : CategoryWeightUiStateResult()
    object Loading : CategoryWeightUiStateResult()
    data class Success(val categoryWeightUiStateList: List<CategoryWeightUiState>) : CategoryWeightUiStateResult()
    data class Failure(val error : String) : CategoryWeightUiStateResult()
}