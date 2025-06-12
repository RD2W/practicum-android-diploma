package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.industry.domain.model.Industry

sealed interface GetIndustriesListResult {
    data class Success(val industries: List<Industry>) : GetIndustriesListResult
    object Problem : GetIndustriesListResult
}
