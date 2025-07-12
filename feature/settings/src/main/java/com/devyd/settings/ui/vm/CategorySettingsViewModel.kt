package com.devyd.settings.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.common.Constants
import com.devyd.domain.models.CategoryWeight
import com.devyd.domain.usecase.categoryweight.AddCategoryWeightUseCase
import com.devyd.domain.usecase.categoryweight.DeleteCategoryWeightUseCase
import com.devyd.domain.usecase.categoryweight.GetCategoryWeightsUseCase
import com.devyd.domain.usecase.categoryweight.UpdateCategoryWeightUseCase
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
    private val _categoryWeights = MutableStateFlow<List<CategoryWeight>>(emptyList())
    val categoryWeights = _categoryWeights.asStateFlow()

    private val _scroll = MutableSharedFlow<Boolean>()
    val scroll = _scroll.asSharedFlow()

    private var categoryId = AtomicInteger(1)
    val categories = Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST

    init {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                getCategoryWeightsUseCase()
            }
            _categoryWeights.value = list
            val maxId = list.maxOfOrNull { it.id } ?: 0
            categoryId = AtomicInteger(maxId + 1)
        }
    }

    fun addSelection() {
        val newItem = CategoryWeight(categoryId.getAndIncrement(), categories.first(), 0)
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                addCategoryWeightUseCase(newItem)
                getCategoryWeightsUseCase()
            }
            _categoryWeights.value = list
            _scroll.emit(true)
        }

    }

    fun updateSelection(id: Int, category: String, weight: Int) {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                updateCategoryWeightUseCase(CategoryWeight(id, category, weight))
                getCategoryWeightsUseCase()
            }
            _categoryWeights.value = list
        }
    }

    fun deleteSelection(categoryWeight: CategoryWeight) {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                deleteCategoryWeightUseCase(categoryWeight)
                getCategoryWeightsUseCase()
            }
            _categoryWeights.value = list
        }
    }
}