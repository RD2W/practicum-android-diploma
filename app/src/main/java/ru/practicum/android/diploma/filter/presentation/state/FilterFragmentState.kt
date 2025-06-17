package ru.practicum.android.diploma.filter.presentation.state

interface FilterFragmentState {
    data class Content(
        val workplaceName: String?,
        val industryName: String?,
        val salary: Salary?,
        val salaryMustHaveFlag: Boolean? = false,
        val skipButtonVisibility: Boolean = false,
    ) : FilterFragmentState
    object Loading : FilterFragmentState
}
