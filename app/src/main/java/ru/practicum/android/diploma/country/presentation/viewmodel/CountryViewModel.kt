package ru.practicum.android.diploma.country.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.country.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.country.presentation.state.CountryFragmentState

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesListUseCase
) : ViewModel() {

    private val countryFragmentStateLiveData = MutableLiveData<CountryFragmentState>()
    fun observeCountryFragmentState(): LiveData<CountryFragmentState> = countryFragmentStateLiveData

    fun getCountries() {
        /*
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { result ->
                when (result) {
                    is GetCountriesListResult.Success -> {
                        renderCountryFragment(CountryFragmentState.Content(result.countries))
                    }

                    is GetCountriesListResult.Problem -> {
                        renderCountryFragment(CountryFragmentState.Empty)
                    }
                }
            }
        }

         */
        val c1 = Country("1", "Заглушка1")
        val c2 = Country("2", "Заглушка2")
        val list = listOf(c1, c2)
        renderCountryFragment(CountryFragmentState.Content(list))
    }

    private fun renderCountryFragment(state: CountryFragmentState) {
        countryFragmentStateLiveData.postValue(state)
    }
}
