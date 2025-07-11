package com.devyd.domain.usecase.categoryweight

import com.devyd.domain.repository.CategoryWeightRepository
import javax.inject.Inject

class DeleteCategoryWeightUseCase @Inject constructor(
    private val categoryWeightRepository: CategoryWeightRepository
) {
    suspend operator fun invoke(id: Int) = categoryWeightRepository.delete(id)
}