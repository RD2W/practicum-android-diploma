package ru.practicum.android.diploma.industry.presentation.state

import ru.practicum.android.diploma.industry.domain.model.Industry

sealed interface IndustryFragmentState {

    data class Content(val industries: List<Industry>) : IndustryFragmentState
    object Problem : IndustryFragmentState
    object Loading : IndustryFragmentState
}
