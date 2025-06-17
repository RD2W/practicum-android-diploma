package ru.practicum.android.diploma.region.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.region.domain.model.AreaRegion
import ru.practicum.android.diploma.region.domain.usecase.GetAreasListUseCase
import ru.practicum.android.diploma.region.presentation.state.AreaFragmentState

class RegionViewModel(
    private val getAreasListUseCase: GetAreasListUseCase
) : ViewModel() {

    private val areaFragmentStateLiveData = MutableLiveData<AreaFragmentState>()
    fun observeAreaFragmentState(): LiveData<AreaFragmentState> = areaFragmentStateLiveData

    fun getAreas(flag: String?) {
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
        var list = emptyList<AreaRegion>()
        val a1 = AreaRegion("1", "Республика Чувашия")
        val a2 = AreaRegion("2", "Республика Мордовия")
        val a3 = AreaRegion("3", "Республика Татарстан")
        val a4 = AreaRegion("4", "Республика Бурятия")
        val a5 = AreaRegion("5", "Приморский край")
        val a6 = AreaRegion("6", "Калининградская область")
        if (flag == "1") {
            list = listOf(a1, a2, a3, a4, a5, a6)
        }

        renderCountryFragment(AreaFragmentState.Content(list))
    }

    private fun renderCountryFragment(state: AreaFragmentState) {
        areaFragmentStateLiveData.postValue(state)
    }
}
