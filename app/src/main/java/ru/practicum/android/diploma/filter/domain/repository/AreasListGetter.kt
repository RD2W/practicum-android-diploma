package ru.practicum.android.diploma.filter.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.GetAreasListResult

interface AreasListGetter {
    fun getAreasList(countryId: String): Flow<GetAreasListResult>
}
