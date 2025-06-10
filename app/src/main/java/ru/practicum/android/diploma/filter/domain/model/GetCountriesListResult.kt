package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.region.domain.model.Country

sealed interface GetCountriesListResult {
    data class Success(val countries: List<Country>) : GetCountriesListResult
    object Problem : GetCountriesListResult
}
