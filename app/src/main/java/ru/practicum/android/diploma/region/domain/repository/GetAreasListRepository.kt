package ru.practicum.android.diploma.region.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.region.domain.model.GetAreasListResult

interface GetAreasListRepository {
    fun getAreasList(countryId: String): Flow<GetAreasListResult>
}
