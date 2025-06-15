package ru.practicum.android.diploma.industry.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.industry.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.industry.presentation.state.IndustryFragmentState

class IndustryViewModel(
    private val getIndustriesListUseCase: GetIndustriesListUseCase
) : ViewModel() {

    private val industryFragmentStateLiveData = MutableLiveData<IndustryFragmentState>()
    fun observeIndustryFragmentState(): LiveData<IndustryFragmentState> = industryFragmentStateLiveData

    fun getIndustries() {
        /*
        viewModelScope.launch {
            getIndustriesUseCase.execute().collect { result ->
                when (result) {
                    is GetIndustriesListResult.Success -> {
                        renderIndustryFragment(IndustryFragmentState.Content(result.industries))
                    }

                    is GetIndustriesListResult.Problem -> {
                        renderUndustryFragment(IndustryFragmentState.Empty)
                    }
                }
            }
        }

         */
        val i1 = Industry("1", "Отрасль заглушка1")
        val i2 = Industry("2", "Отрасль заглушка2")
        val i3 = Industry("3", "Отрасль заглушка3")
        val i4 = Industry("4", "Отрасль заглушка4")
        val i5 = Industry("5", "Отрасль заглушка35")
        val i6 = Industry("6", "Отрасль заглушка53")
        val i7 = Industry("7", "Отрасль заглушка5")
        val i8 = Industry("8", "Отрасль заглушка6")
        val i9 = Industry("9", "Отрасль заглушка7")
        val i10 = Industry("10", "Отрасль заглушка71")
        val i11 = Industry("11", "Отрасль заглушка8")
        val i12 = Industry("12", "Отрасль заглушка9")
        val list = listOf(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12)
        renderIndustryFragment(IndustryFragmentState.Content(list))
    }

    private fun renderIndustryFragment(state: IndustryFragmentState) {
        industryFragmentStateLiveData.postValue(state)
    }
}
