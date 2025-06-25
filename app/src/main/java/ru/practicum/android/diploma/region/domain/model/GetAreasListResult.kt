package ru.practicum.android.diploma.region.domain.model

sealed interface GetAreasListResult {
    data class Success(val areas: List<AreaRegion>) : GetAreasListResult
    object Error : GetAreasListResult
}
