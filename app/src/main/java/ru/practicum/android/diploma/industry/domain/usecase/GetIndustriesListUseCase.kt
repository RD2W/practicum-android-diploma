package ru.practicum.android.diploma.industry.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industry.domain.model.GetIndustriesListResult

interface GetIndustriesListUseCase {
    fun execute(): Flow<GetIndustriesListResult>
}
