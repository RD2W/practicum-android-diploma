package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.region.domain.model.Area

interface FilterRepository {
    suspend fun getCountries(): Flow<RequestResult<List<Country>>>
    suspend fun getAreas(id: String?): Flow<RequestResult<List<Area>>>
    suspend fun getIndustries(): Flow<RequestResult<List<Industry>>>
}
