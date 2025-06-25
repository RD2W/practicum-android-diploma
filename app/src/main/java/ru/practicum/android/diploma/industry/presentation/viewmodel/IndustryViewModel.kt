package ru.practicum.android.diploma.industry.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.industry.domain.model.GetIndustriesListResult
import ru.practicum.android.diploma.industry.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.industry.presentation.state.IndustryFragmentState

class IndustryViewModel(
    private val getIndustriesListUseCase: GetIndustriesListUseCase
) : ViewModel() {

    private val industryFragmentStateLiveData = MutableLiveData<IndustryFragmentState>()
    fun observeIndustryFragmentState(): LiveData<IndustryFragmentState> = industryFragmentStateLiveData

    fun getIndustries() {
        renderIndustryFragment(IndustryFragmentState.Loading)
        viewModelScope.launch {
            getIndustriesListUseCase.execute().collect { result ->
                when (result) {
                    is GetIndustriesListResult.Success -> {
                        renderIndustryFragment(IndustryFragmentState.Content(result.industries))
                    }

                    is GetIndustriesListResult.Error -> {
                        renderIndustryFragment(IndustryFragmentState.Error)
                    }
                }
            }
        }
    }

    private fun renderIndustryFragment(state: IndustryFragmentState) {
        industryFragmentStateLiveData.postValue(state)
    }
}
