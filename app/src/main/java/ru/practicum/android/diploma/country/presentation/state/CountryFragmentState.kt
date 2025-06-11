package ru.practicum.android.diploma.country.presentation.state

import ru.practicum.android.diploma.country.domain.model.Country

sealed interface CountryFragmentState {

    data class Content(val countries: List<Country>) : CountryFragmentState
    object Empty : CountryFragmentState
}
