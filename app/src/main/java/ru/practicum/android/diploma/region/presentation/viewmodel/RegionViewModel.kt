package ru.practicum.android.diploma.region.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.region.domain.model.GetAreasListResult
import ru.practicum.android.diploma.region.domain.usecase.GetAreasListUseCase
import ru.practicum.android.diploma.region.presentation.state.AreaFragmentState

class RegionViewModel(
    private val getAreasListUseCase: GetAreasListUseCase
) : ViewModel() {

    private val areaFragmentStateLiveData = MutableLiveData<AreaFragmentState>()
    fun observeAreaFragmentState(): LiveData<AreaFragmentState> = areaFragmentStateLiveData

    fun getAreas(countryId: String) {
        renderRegionFragment(AreaFragmentState.Loading)
        viewModelScope.launch {
            getAreasListUseCase.execute(countryId).collect { result ->
                when (result) {
                    is GetAreasListResult.Success -> {
                        renderRegionFragment(AreaFragmentState.Content(result.areas))
                    }

                    is GetAreasListResult.Problem -> {
                        renderRegionFragment(AreaFragmentState.Error)
                    }
                }
            }
        }
    }

    private fun renderRegionFragment(state: AreaFragmentState) {
        areaFragmentStateLiveData.postValue(state)
    }
}
