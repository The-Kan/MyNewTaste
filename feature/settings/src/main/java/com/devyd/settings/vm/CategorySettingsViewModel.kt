package com.devyd.settings.vm

import androidx.lifecycle.ViewModel
import com.devyd.common.Constants
import com.devyd.settings.model.CategoryWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategorySettingsViewModel @Inject constructor() : ViewModel() {
    val categories = Constants.NEWS_API_TOP_HEADLINES_CATEGORY_LIST

    private val categoryWeightList = ArrayList<CategoryWeight>()
    var categoryWeightItemId = 1

    fun addSelection() {
        val cw = CategoryWeight(categoryWeightItemId++, categories[0], 0)
        categoryWeightList.add(cw)
    }

    fun modifySelection(id: Int, category: String, weight: Int) {
        val selection = categoryWeightList.last { it.id == id }
        selection.category = category
        selection.weight = weight
    }

    fun deleteSelection(id: Int) {
        categoryWeightList.removeIf { it.id == id }
    }

    fun getCategoryWeightList(): List<CategoryWeight> {
        return categoryWeightList.map { it.copy() }
    }
}