package com.devyd.settings.vm

import androidx.lifecycle.ViewModel
import com.devyd.common.Constants
import com.devyd.settings.model.CategoryWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class CategorySettingsViewModel @Inject constructor() : ViewModel() {
    private val _categoryWeights = MutableStateFlow<List<CategoryWeight>>(emptyList())
    val categoryWeights = _categoryWeights.asStateFlow()

    private val categoryId = AtomicInteger(1)
    val categories = Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST

    fun addSelection() {
        val newItem = CategoryWeight(categoryId.getAndIncrement(), categories.first(), 0)
        _categoryWeights.update { oldList -> oldList + newItem }
    }

    fun modifySelection(id: Int, category: String, weight: Int) {
        _categoryWeights.update { oldList ->
            oldList.map { if (it.id == id) it.copy(category = category, weight = weight) else it }
        }
    }

    fun deleteSelection(id: Int) {
        _categoryWeights.update { oldList -> oldList.filterNot { it.id == id } }
    }
}