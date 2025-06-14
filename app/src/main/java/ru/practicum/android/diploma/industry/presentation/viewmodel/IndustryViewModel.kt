package ru.practicum.android.diploma.industry.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.industry.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.industry.presentation.state.IndustryFragmentState

class IndustryViewModel(
    private val getIndustriesListUseCase: GetIndustriesListUseCase
) : ViewModel() {

    private val industryFragmentStateLiveData = MutableLiveData<IndustryFragmentState>()
    fun observeIndustryFragmentState(): LiveData<IndustryFragmentState> = industryFragmentStateLiveData

    fun getIndustries() {
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
        val i1 = Industry("1", "Отрасль заглушка1")
        val i2 = Industry("2", "Отрасль заглушка2")
        val i3 = Industry("3", "Отрасль заглушка3")
        val i4 = Industry("4", "Отрасль заглушка4")
        val list = listOf(i1, i2, i3, i4)
        renderIndustryFragment(IndustryFragmentState.Content(list))
    }

    private fun renderIndustryFragment(state: IndustryFragmentState) {
        industryFragmentStateLiveData.postValue(state)
    }
}
