package ru.practicum.android.diploma.filter.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.filter.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.industry.domain.model.Industry

class GetIndustriesUseCaseImpl(
    private val repository: FilterRepository,
): GetIndustriesUseCase {
    override suspend operator fun invoke(): Flow<RequestResult<List<Industry>>> = repository.getIndustries()
}
