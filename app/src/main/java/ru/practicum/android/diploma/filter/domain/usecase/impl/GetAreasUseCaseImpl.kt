package ru.practicum.android.diploma.filter.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.filter.domain.usecase.GetAreasUseCase
import ru.practicum.android.diploma.region.domain.model.Area

class GetAreasUseCaseImpl(
    private val repository: FilterRepository,
) : GetAreasUseCase {
    override suspend fun invoke(id: String?): Flow<RequestResult<List<Area>>> = repository.getAreas(id)
}
