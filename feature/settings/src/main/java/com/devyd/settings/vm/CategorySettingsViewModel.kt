package com.devyd.settings.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyd.common.Constants
import com.devyd.domain.models.CategoryWeight
import com.devyd.domain.usecase.categoryweight.AddCategoryWeightUseCase
import com.devyd.domain.usecase.categoryweight.DeleteCategoryWeightUseCase
import com.devyd.domain.usecase.categoryweight.GetCategoryWeightsUseCase
import com.devyd.domain.usecase.categoryweight.UpdateCategoryWeightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class CategorySettingsViewModel @Inject constructor(
    private val getCategoryWeightsUseCase : GetCategoryWeightsUseCase,
    private val addCategoryWeightUseCase : AddCategoryWeightUseCase,
    private val updateCategoryWeightUseCase : UpdateCategoryWeightUseCase,
    private val deleteCategoryWeightUseCase : DeleteCategoryWeightUseCase,
    ) : ViewModel() {
    private val _categoryWeights = MutableStateFlow<List<CategoryWeight>>(emptyList())
    val categoryWeights = _categoryWeights.asStateFlow()

    private val categoryId = AtomicInteger(1)
    val categories = Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST

    init {
        viewModelScope.launch {
            _categoryWeights.update { getCategoryWeightsUseCase() }
        }
    }

    fun addSelection() {
        val newItem = CategoryWeight(categoryId.getAndIncrement(), categories.first(), 0)
        viewModelScope.launch {
            addCategoryWeightUseCase(newItem)
            _categoryWeights.update { getCategoryWeightsUseCase() }
        }

    }

    fun updateSelection(id: Int, category: String, weight: Int) {
        viewModelScope.launch {
            updateCategoryWeightUseCase(CategoryWeight(id, category, weight))
            _categoryWeights.update { getCategoryWeightsUseCase() }
        }
    }

    fun deleteSelection(id: Int) {
        viewModelScope.launch {
            deleteCategoryWeightUseCase(id)
            _categoryWeights.update { getCategoryWeightsUseCase() }
        }
    }
}