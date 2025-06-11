package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase
import timber.log.Timber

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {
    private val testQuery = SearchParameters("android разработчик")

    fun doSearch() {
        viewModelScope.launch {
            searchUseCase(testQuery).collect {
                Timber.d("parsing results here...")
            }
        }
    }
}
