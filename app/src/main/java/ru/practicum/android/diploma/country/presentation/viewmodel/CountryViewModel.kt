package ru.practicum.android.diploma.country.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.region.domain.model.Country

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesListUseCase
) : ViewModel() {

    private val countriesList = ArrayList<Country>()

    fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { result ->
                when (result) {
                    is GetCountriesListResult.Success -> {
                        countriesList.addAll(result.countries)
                        renderCountriesAdapter()
                    }

                    is GetCountriesListResult.Problem -> {}
                }
            }
        }
    }

    private fun renderCountriesAdapter() {

    }
}
