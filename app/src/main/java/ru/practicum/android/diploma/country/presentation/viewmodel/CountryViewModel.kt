package ru.practicum.android.diploma.country.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.country.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.country.presentation.state.CountryFragmentState

class CountryViewModel(
    private val getCountriesListUseCase: GetCountriesListUseCase
) : ViewModel() {

    private val countryFragmentStateLiveData = MutableLiveData<CountryFragmentState>()
    fun observeCountryFragmentState(): LiveData<CountryFragmentState> = countryFragmentStateLiveData

    fun getCountries() {
        /*
        viewModelScope.launch {
            getCountriesListUseCase.execute().collect { result ->
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
        val c1 = Country("1", "Россия")
        val c2 = Country("2", "Китай")
        val c3 = Country("3", "КНДР")
        val c4 = Country("4", "Япония")
        val c5 = Country("5", "Белоруссия")
        val c6 = Country("6", "Австралия")
        val list = listOf(c1, c2, c3, c4, c5, c6)
        renderCountryFragment(CountryFragmentState.Content(list))
    }

    private fun renderCountryFragment(state: CountryFragmentState) {
        countryFragmentStateLiveData.postValue(state)
    }
}
