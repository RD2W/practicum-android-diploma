package ru.practicum.android.diploma.filter.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.industry.domain.model.Industry

interface GetIndustriesUseCase {
    suspend operator fun invoke(): Flow<RequestResult<List<Industry>>>
}
