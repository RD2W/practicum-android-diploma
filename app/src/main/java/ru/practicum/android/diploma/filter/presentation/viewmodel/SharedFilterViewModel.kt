package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.industry.domain.model.Industry

class SharedFilterViewModel : ViewModel() {

    private val currentIndustry: SingleLiveEvent<Industry?> = SingleLiveEvent()
    private val currentCountry: SingleLiveEvent<Country?> = SingleLiveEvent()
    private val currentArea: SingleLiveEvent<AreaRegion?> = SingleLiveEvent()
    private val currentWorkplace: SingleLiveEvent<Workplace?> = SingleLiveEvent()

    fun getIndustry(): MutableLiveData<Industry?> = currentIndustry
    fun setIndustry(industry: Industry) {
        currentIndustry.value = industry
    }

    fun getCountry(): MutableLiveData<Country?> = currentCountry
    fun setCountry(country: Country) {
        currentCountry.value = country
    }

    fun getArea(): MutableLiveData<AreaRegion?> = currentArea
    fun setArea(area: AreaRegion) {
        currentArea.value = area
    }

    fun getWorkplace(): MutableLiveData<Workplace?> = currentWorkplace
    fun setWorkplace(workplace: Workplace) {
        currentWorkplace.value = workplace
    }

    fun resetValues() {
        currentWorkplace.value = null
        currentIndustry.value = null
        currentCountry.value = null
        currentArea.value = null
    }
}
