package ru.practicum.android.diploma.country.domain.model

sealed interface GetCountriesListResult {
    data class Success(val countries: List<Country>) : GetCountriesListResult
    object Error : GetCountriesListResult
}
