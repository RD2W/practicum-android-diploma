package ru.practicum.android.diploma.industry.domain.model

sealed interface GetIndustriesListResult {
    data class Success(val industries: List<Industry>) : GetIndustriesListResult
    object Error : GetIndustriesListResult
}
