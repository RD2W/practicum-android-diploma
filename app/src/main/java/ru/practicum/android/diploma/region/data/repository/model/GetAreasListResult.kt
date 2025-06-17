package ru.practicum.android.diploma.region.data.repository.model

import ru.practicum.android.diploma.region.domain.model.Area

sealed interface GetAreasListResult {
    data class Success(val areas: List<Area>) : GetAreasListResult
    object Problem : GetAreasListResult
}
