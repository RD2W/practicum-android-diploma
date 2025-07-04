package ru.practicum.android.diploma.filter.presentation.state

import ru.practicum.android.diploma.filter.domain.model.Salary

interface FilterFragmentState {
    data class Content(
        val workplaceName: String?,
        val countryId: String?, // new
        val countryName: String?, // new
        val areaName: String?, // new
        val industryId: String?,
        val industryName: String?,
        val salary: Salary?,
        val salaryMustHaveFlag: Boolean? = false,
        val skipButtonVisibility: Boolean = false,
    ) : FilterFragmentState
    object Loading : FilterFragmentState
}
