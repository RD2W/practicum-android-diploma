package ru.practicum.android.diploma.region.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.region.domain.model.GetAreasListResult

interface GetAreasListUseCase {
    fun execute(countryId: String): Flow<GetAreasListResult>
}
