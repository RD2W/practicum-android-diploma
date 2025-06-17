package ru.practicum.android.diploma.region.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.region.domain.model.Area

interface GetAreasListUseCase {
    suspend operator fun invoke(id: String?): Flow<RequestResult<List<Area>>>
}
