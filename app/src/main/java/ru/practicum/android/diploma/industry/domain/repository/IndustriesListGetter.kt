package ru.practicum.android.diploma.industry.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industry.domain.model.GetIndustriesListResult

interface IndustriesListGetter {
    fun getIndustriesList(): Flow<GetIndustriesListResult>
}
