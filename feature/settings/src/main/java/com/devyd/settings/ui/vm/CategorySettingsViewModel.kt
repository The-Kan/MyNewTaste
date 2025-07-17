package com.devyd.settings.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.common.Constants
import com.devyd.domain.usecase.categoryweight.AddCategoryWeightUseCase
import com.devyd.domain.usecase.categoryweight.DeleteCategoryWeightUseCase
import com.devyd.domain.usecase.categoryweight.GetCategoryWeightsUseCase
import com.devyd.domain.usecase.categoryweight.UpdateCategoryWeightUseCase
import com.devyd.ui.models.CategoryWeightUiState
import com.devyd.ui.models.CategoryWeightUiStateResult
import com.devyd.ui.models.toDomain
import com.devyd.ui.models.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class CategorySettingsViewModel @Inject constructor(
    private val getCategoryWeightsUseCase: GetCategoryWeightsUseCase,
    private val addCategoryWeightUseCase: AddCategoryWeightUseCase,
    private val updateCategoryWeightUseCase: UpdateCategoryWeightUseCase,
    private val deleteCategoryWeightUseCase: DeleteCategoryWeightUseCase,
) : ViewModel() {
    private val _categoryWeights = MutableStateFlow<CategoryWeightUiStateResult>(CategoryWeightUiStateResult.Idle)
    val categoryWeights = _categoryWeights.asStateFlow()

    private val _scroll = MutableSharedFlow<Boolean>()
    val scroll = _scroll.asSharedFlow()

    private val _addCategoryPossible = MutableStateFlow(true)
    val addCategoryPossible = _addCategoryPossible.asStateFlow()

    private var categoryId = AtomicInteger(1)
    val categories = Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST

    init {
        viewModelScope.launch {
            _categoryWeights.value = CategoryWeightUiStateResult.Loading

            val result = kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    getCategoryWeightsUseCase()
                }
            }.fold(
                onSuccess = { list ->
                    val maxId = list.maxOfOrNull { it.id } ?: 0
                    categoryId = AtomicInteger(maxId + 1)

                    val resultList = list.map { it.toUiState() }
                    updateAddCategoryPossible(resultList)

                    CategoryWeightUiStateResult.Success(resultList)
                },
                onFailure = { error ->
                    CategoryWeightUiStateResult.Failure(
                        error.message ?: "unknown error"
                    )
                })

            _categoryWeights.value = result
        }
    }

    private fun updateAddCategoryPossible(list: List<CategoryWeightUiState>) {
        _addCategoryPossible.value = list.size < categories.size
    }

    fun addSelection(category: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    val newItem =
                        CategoryWeightUiState(categoryId.getAndIncrement(), category, 1).toDomain()
                    addCategoryWeightUseCase(newItem)
                    getCategoryWeightsUseCase()
                }
            }.fold(
                onSuccess = { list ->
                    val resultList = list.map { it.toUiState() }
                    updateAddCategoryPossible(resultList)
                    CategoryWeightUiStateResult.Success(resultList)
                },
                onFailure = { error ->
                    CategoryWeightUiStateResult.Failure(
                        error.message ?: "unknown error"
                    )
                })

            _categoryWeights.value = result
            _scroll.emit(true)
        }
    }

    fun updateSelection(id: Int, category: String, weight: Int) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                withContext(Dispatchers.IO) {

                    updateCategoryWeightUseCase(CategoryWeightUiState(id,category,weight).toDomain())
                    getCategoryWeightsUseCase()
                }
            }.fold(
                onSuccess = { list ->
                    val resultList = list.map { it.toUiState() }
                    CategoryWeightUiStateResult.Success(resultList)
                },
                onFailure = { error ->
                    CategoryWeightUiStateResult.Failure(
                        error.message ?: "unknown error"
                    )
                })

            _categoryWeights.value = result
        }
    }

    fun deleteSelection(categoryWeightUiState: CategoryWeightUiState) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    deleteCategoryWeightUseCase(categoryWeightUiState.toDomain())
                    getCategoryWeightsUseCase()
                }
            }.fold(
                onSuccess = { list ->
                    val resultList = list.map { it.toUiState() }
                    updateAddCategoryPossible(resultList)
                    CategoryWeightUiStateResult.Success(resultList)
                },
                onFailure = { error ->
                    CategoryWeightUiStateResult.Failure(
                        error.message ?: "unknown error"
                    )
                })

            _categoryWeights.value = result
        }
    }
}