package ru.practicum.android.diploma.region.presentation.state

import ru.practicum.android.diploma.region.domain.model.AreaRegion

sealed interface AreaFragmentState {
    data class Content(val areas: List<AreaRegion>) : AreaFragmentState
    object Problem : AreaFragmentState
    object Loading : AreaFragmentState
}
