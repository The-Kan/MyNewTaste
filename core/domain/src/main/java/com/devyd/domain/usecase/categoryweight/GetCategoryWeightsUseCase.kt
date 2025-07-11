package com.devyd.domain.usecase.categoryweight

import com.devyd.domain.repository.CategoryWeightRepository
import javax.inject.Inject

class GetCategoryWeightsUseCase @Inject constructor(
    private val categoryWeightRepository: CategoryWeightRepository
) {
    suspend operator fun invoke() = categoryWeightRepository.getAll()
}