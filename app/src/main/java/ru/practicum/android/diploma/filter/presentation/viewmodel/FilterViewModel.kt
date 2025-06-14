package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.presentation.state.FilterFragmentState
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.region.domain.model.Area

class FilterViewModel : ViewModel() {

    private var filter: Filter? = null
    private var area: Area? = null
    private var industry: Industry? = null
    private var desiredSalary: Int? = null
    private var salaryMustHaveFlag: Boolean = false

    private val filterFragmentStateLiveData = MutableLiveData<FilterFragmentState>()
    fun observeFilterFragmentState(): LiveData<FilterFragmentState> = filterFragmentStateLiveData

    fun updateValues(
        area: Area? = null,
        industry: Industry? = null,
        desiredSalary: Int? = null,
        salaryMustHaveFlag: Boolean? = null,
    ) {
        if (area != null) {
            this.area = area
        }
        if (industry != null) {
            this.industry = industry
        }
        if (desiredSalary != null) {
            this.desiredSalary = desiredSalary
        }
        if (salaryMustHaveFlag != null) {
            this.salaryMustHaveFlag = salaryMustHaveFlag
        }
        updateState()
    }

    private fun updateState() {
        val filterFragmentState = FilterFragmentState.Content(
            areaName = this.area?.name,
            industryName = this.industry?.name,
            desiredSalary = this.desiredSalary,
            salaryMustHaveFlag = this.salaryMustHaveFlag,
            skipButtonVisibility = !filterParamsIsNull()
        )
        renderFilterFragment(filterFragmentState)
    }

    private fun renderFilterFragment(state: FilterFragmentState) {
        filterFragmentStateLiveData.postValue(state)
    }

    private fun filterParamsIsNull(): Boolean {
        return ((this.area == null)
            && (this.industry == null)
            && (this.desiredSalary == null)) && !this.salaryMustHaveFlag
    }

    fun skipValuesAndFilter() {
        this.area = null
        this.industry = null
        this.desiredSalary = null
        this.salaryMustHaveFlag = false
        this.filter = null
        updateState()
    }
}
