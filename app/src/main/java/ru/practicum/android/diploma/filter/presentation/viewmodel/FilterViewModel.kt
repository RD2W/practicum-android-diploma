package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.domain.model.SingleLiveEvent
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.model.Salary
import ru.practicum.android.diploma.filter.domain.usecase.CheckFilterLoadUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetFilterUseCase
import ru.practicum.android.diploma.filter.domain.usecase.SetFilterUseCase
import ru.practicum.android.diploma.filter.presentation.state.FilterFragmentState
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.workplace.domain.model.Workplace

class FilterViewModel(
    private val setFilterUseCase: SetFilterUseCase,
    private val getFilterUseCase: GetFilterUseCase,
    private val checkFilterLoadUseCase: CheckFilterLoadUseCase
) : ViewModel() {

    private var filter: Filter? = null
    private var workplace: Workplace? = null
    private var industry: Industry? = null
    private var salary: Salary? = null
    private var salaryMustHaveFlag: Boolean? = null

    private val filterFragmentStateLiveData = MutableLiveData<FilterFragmentState>()
    fun observeFilterFragmentState(): LiveData<FilterFragmentState> = filterFragmentStateLiveData

    private val filterApplyButtonStateLiveData = SingleLiveEvent<Boolean>()
    fun observeFilterApplyButtonState(): LiveData<Boolean> = filterApplyButtonStateLiveData

    private val filterSkipButtonStateLiveData = SingleLiveEvent<Boolean>()
    fun observeFilterSkipButtonState(): LiveData<Boolean> = filterSkipButtonStateLiveData

    fun updateSalaryMustHaveFlagValue(isChecked: Boolean) {
        if (isChecked) {
            this.salaryMustHaveFlag = true
        } else {
            this.salaryMustHaveFlag = null
        }
    }

    fun updateSalaryValue(salarySum: Int?) {
        if (salarySum != null) {
            this.salary = Salary(from = salarySum)
            return
        }
        this.salary = null
    }

    fun synchronizeState(
        workplace: Workplace? = null,
        industry: Industry? = null,
        salary: Salary? = null,
        salaryMustHaveFlag: Boolean? = null,
    ) {
        loadFilter()
        passFilterToValues()
        passFragmentNotNullDataToValues(workplace, industry, salary, salaryMustHaveFlag)
        updateState()
    }

    private fun loadFilter() {
        filter = getFilterUseCase.execute()
    }

    private fun passFilterToValues() {
        if (this.workplace == null) this.workplace = filter?.workplace
        if (this.industry == null) this.industry = filter?.industry
        if (this.salary == null) this.salary = filter?.salary
        if (this.salaryMustHaveFlag == null) this.salaryMustHaveFlag = filter?.onlyWithSalary
    }

    private fun passFragmentNotNullDataToValues(
        workplace: Workplace? = null,
        industry: Industry? = null,
        salary: Salary? = null,
        salaryMustHaveFlag: Boolean? = null,
    ) {
        this.workplace = workplace ?: this.workplace
        this.industry = industry ?: this.industry
        this.salary = salary ?: this.salary
        this.salaryMustHaveFlag = salaryMustHaveFlag ?: this.salaryMustHaveFlag
    }

    private fun updateState() {
        val filterFragmentState = FilterFragmentState.Content(
            workplaceName = concatWorkplaceName(this.workplace),
            industryName = this.industry?.name,
            salary = this.salary,
            salaryMustHaveFlag = this.salaryMustHaveFlag,
            skipButtonVisibility = filterSavedParamsAreNotNull()
        )
        renderFilterFragment(filterFragmentState)
    }

    private fun renderFilterFragment(state: FilterFragmentState) {
        filterFragmentStateLiveData.postValue(state)
    }

    private fun concatWorkplaceName(workplace: Workplace?): String? {
        if (workplace == null) {
            return null
        } else {
            var workplaceName = workplace.countryName
            if (workplace.areaName != null) {
                workplaceName = workplaceName + ", " + workplace.areaName
            }
            return workplaceName
        }
    }

    private fun filterSavedParamsAreNotNull(): Boolean {
        return checkFilterLoadUseCase.execute()
    }

    fun skipValuesAndFilter() {
        this.workplace = null
        this.industry = null
        this.salary = null
        this.salaryMustHaveFlag = false
        this.filter = null
        skipFilter()
        updateState()
    }

    private fun skipFilter() {
        val filterNull = Filter(
            workplace = null,
            industry = null,
            salary = null,
            onlyWithSalary = false
        )
        setFilterUseCase.execute(filterNull)
    }

    fun applyFilter() {
        val filterLoaded = Filter(
            workplace = this.workplace,
            industry = this.industry,
            salary = this.salary,
            onlyWithSalary = this.salaryMustHaveFlag
        )
        setFilterUseCase.execute(filterLoaded)
        synchronizeState()
    }

    private fun filterAndValuesAreEqual(): Boolean {
        return filter?.workplace == this.workplace
            && filter?.industry == this.industry
            && filter?.salary?.from == this.salary?.from
            && filter?.onlyWithSalary == this.salaryMustHaveFlag
    }

    fun checkFilterLoad() {
        renderFilterSkipButtonState(filterSavedParamsAreNotNull())
    }

    fun checkUpdates() {
        renderFilterApplyButtonState(!filterAndValuesAreEqual())
    }

    private fun renderFilterApplyButtonState(setVisible: Boolean) {
        filterApplyButtonStateLiveData.postValue(setVisible)
    }

    private fun renderFilterSkipButtonState(setVisible: Boolean) {
        filterSkipButtonStateLiveData.postValue(setVisible)
    }

}
