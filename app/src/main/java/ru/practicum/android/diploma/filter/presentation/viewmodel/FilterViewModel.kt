package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.usecase.impl.GetAreasUseCaseImpl
import timber.log.Timber

class FilterViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getAreasUseCaseImpl: GetAreasUseCaseImpl
) : ViewModel() {
    fun doSearch() {
        viewModelScope.launch {
            getCountriesUseCase().collect {
                Timber.d("parsing results here...")
            }
        }
    }
}
