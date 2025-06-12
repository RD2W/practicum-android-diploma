package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.GetIndustriesListResult

interface IndustriesListGetter {
    fun getIndustriesList(): Flow<GetIndustriesListResult>
}
