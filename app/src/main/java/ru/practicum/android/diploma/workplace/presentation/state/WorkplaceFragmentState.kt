package ru.practicum.android.diploma.workplace.presentation.state

import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.region.domain.model.AreaRegion

sealed interface WorkplaceFragmentState {
    data class Content(val country: Country?, val area: AreaRegion?) : WorkplaceFragmentState
    object Loading : WorkplaceFragmentState
}
