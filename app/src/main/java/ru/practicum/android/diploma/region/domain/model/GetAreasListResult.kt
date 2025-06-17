package ru.practicum.android.diploma.region.domain.model

sealed interface GetAreasListResult {
    data class Success(val areas: List<Area>) : GetAreasListResult
    object Problem : GetAreasListResult
}
