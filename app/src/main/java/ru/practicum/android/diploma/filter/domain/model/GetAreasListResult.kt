package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.region.domain.model.Area

sealed interface GetAreasListResult {
    data class Success(val areas: List<Area>) : GetAreasListResult
    object Problem : GetAreasListResult
}
