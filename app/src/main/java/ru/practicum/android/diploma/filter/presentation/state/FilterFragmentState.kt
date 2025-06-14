package ru.practicum.android.diploma.filter.presentation.state

interface FilterFragmentState {

    data class Content(
        val areaName: String?,
        val industryName: String?,
        val desiredSalary: Int?,
        val salaryMustHaveFlag: Boolean = false,
        val skipButtonVisibility: Boolean = false,
    ) : FilterFragmentState
    object Loading : FilterFragmentState
}
