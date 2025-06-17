package ru.practicum.android.diploma.country.data.repository.model

import ru.practicum.android.diploma.region.domain.model.Country

sealed interface GetCountriesListResult {
    data class Success(val countries: List<Country>) : GetCountriesListResult
    object Problem : GetCountriesListResult
}
