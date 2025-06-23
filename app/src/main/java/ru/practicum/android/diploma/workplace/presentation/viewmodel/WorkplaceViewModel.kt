package ru.practicum.android.diploma.workplace.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.region.domain.model.AreaRegion
import ru.practicum.android.diploma.workplace.presentation.state.WorkplaceFragmentState

class WorkplaceViewModel : ViewModel() {

    private val workplaceFragmentStateLiveData = MutableLiveData<WorkplaceFragmentState>()
    fun observeWorkplaceFragmentState(): LiveData<WorkplaceFragmentState> = workplaceFragmentStateLiveData

    fun synchronizeState(
        country: Country?,
        area: AreaRegion? = null
    ) {
        updateState(country, area)
    }

    private fun updateState(country: Country?, area: AreaRegion?) {
        val workplaceFragmentState = WorkplaceFragmentState.Content(
            country = country,
            area = area
        )
        renderWorkplaceFragment(workplaceFragmentState)
    }

    private fun renderWorkplaceFragment(state: WorkplaceFragmentState) {
        workplaceFragmentStateLiveData.postValue(state)
    }
}
