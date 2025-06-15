package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.industry.domain.model.Industry

class SharedFilterViewModel : ViewModel() {

    private val currentIndustry: SingleLiveEvent<Industry> = SingleLiveEvent()
    fun getIndustry(): MutableLiveData<Industry> = currentIndustry
    fun setIndustry(industry: Industry) {
        currentIndustry.value = industry
    }
}
