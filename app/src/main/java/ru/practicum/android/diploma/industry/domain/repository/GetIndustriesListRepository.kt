package ru.practicum.android.diploma.industry.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industry.data.repository.model.GetIndustriesListResult

interface GetIndustriesListRepository {
    fun getIndustriesList(): Flow<GetIndustriesListResult>
}
