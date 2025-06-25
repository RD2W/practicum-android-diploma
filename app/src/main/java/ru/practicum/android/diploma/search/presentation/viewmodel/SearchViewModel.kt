package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.utils.debounce
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.search.domain.usecase.GetActiveFilterUseCase
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase
import ru.practicum.android.diploma.search.presentation.state.SearchVacanciesScreenState
import timber.log.Timber
import java.io.IOException

/**
 * ViewModel для экрана "Поиск вакансии".
 * Отвечает за:
 * - Загрузку поиска вакансий
 * - Управление состоянием UI
 * - Обработку ошибок
 *
 * @property searchUseCase UseCase для получения списка найденных вакансий
 */
class SearchViewModel(
    private val getFilterUseCase: GetActiveFilterUseCase,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow<SearchVacanciesScreenState>(
        SearchVacanciesScreenState.Initial(hasActiveFilters = false)
    )
    val screenState: StateFlow<SearchVacanciesScreenState> = _screenState.asStateFlow()

    private var lastQuery = ""
    private var currentPage = 0
    private var totalPages = 1
    private val loadedPages = mutableSetOf<Int>()
    private var allVacancies = emptyList<Vacancy>()
    private var isNewSearch: Boolean = true

    /**
     * Дебаунс
     */
    private val debouncedSearch = debounce<String>(
        delayMillis = AppConstants.SEARCH_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { query ->
        performSearch(query, isNewSearch = isNewSearch)
    }

    fun observeFilterChanges() {
        viewModelScope.launch {
            getFilterUseCase().collect { filter ->
                Timber.d("Filter is updated: $filter")
                updateCurrentFilter(filter)
                onFiltersChanged()
            }
        }
    }

    private fun updateCurrentFilter(filter: Filter?) {
        val currentState = _screenState.value
        val hasFiltersNow = filter != null

        if (getCurrentHasActiveFilters(currentState) != hasFiltersNow) {
            val newState = when (currentState) {
                is SearchVacanciesScreenState.Initial -> currentState.copy(hasActiveFilters = hasFiltersNow)
                is SearchVacanciesScreenState.Loading -> currentState.copy(hasActiveFilters = hasFiltersNow)
                is SearchVacanciesScreenState.Pagination -> currentState.copy(hasActiveFilters = hasFiltersNow)
                is SearchVacanciesScreenState.Content -> currentState.copy(hasActiveFilters = hasFiltersNow)
                is SearchVacanciesScreenState.NoResults -> currentState.copy(hasActiveFilters = hasFiltersNow)
                is SearchVacanciesScreenState.NetworkError -> currentState.copy(hasActiveFilters = hasFiltersNow)
                is SearchVacanciesScreenState.ServerError -> currentState.copy(hasActiveFilters = hasFiltersNow)
            }
            _screenState.value = newState
        }
    }

    private fun getCurrentHasActiveFilters(state: SearchVacanciesScreenState): Boolean {
        return when (state) {
            is SearchVacanciesScreenState.Initial -> state.hasActiveFilters
            is SearchVacanciesScreenState.Loading -> state.hasActiveFilters
            is SearchVacanciesScreenState.Pagination -> state.hasActiveFilters
            is SearchVacanciesScreenState.Content -> state.hasActiveFilters
            is SearchVacanciesScreenState.NoResults -> state.hasActiveFilters
            is SearchVacanciesScreenState.NetworkError -> state.hasActiveFilters
            is SearchVacanciesScreenState.ServerError -> state.hasActiveFilters
        }
    }

    /**
     * Обрабатывает изменение поискового запроса
     * @param query Введенный поисковый запрос
     */
    fun onSearchQueryChanged(query: String, isNewSearch: Boolean) {
        val trimmedQuery = query.trim()
        this.isNewSearch = isNewSearch
        // Если запрос не изменился - ничего не делаем
        if (trimmedQuery == lastQuery) {
            Timber.d("Query didn't change: '$trimmedQuery'")
            return
        }

        // Сохраняем новый запрос
        lastQuery = trimmedQuery

        when {
            // Если запрос пустой - сбрасываем состояние
            trimmedQuery.isBlank() -> {
                Timber.d("Empty query, resetting state")
                _screenState.value = SearchVacanciesScreenState.Initial(
                    hasActiveFilters = getCurrentHasActiveFilters(_screenState.value)
                )
                loadedPages.clear()
                allVacancies = emptyList()
            }
            // Запускаем поиск с debounce
            else -> {
                Timber.d("New search query: '$trimmedQuery'")
                debouncedSearch(trimmedQuery)
            }
        }
    }

    /**
     * Настройка изменения фильтров
     */
    private fun onFiltersChanged() {
        if (lastQuery.isNotEmpty()) {
            performSearch(lastQuery, isNewSearch)
        }
    }

    /**
     * Загрузка следующей страницы
     */
    fun loadNextPage() {
        if (currentPage < totalPages && !loadedPages.contains(currentPage + 1)) {
            performSearch(lastQuery, isNewSearch = false)
        }
    }

    private fun performSearch(query: String, isNewSearch: Boolean) {
        if (query.isBlank()) return

        updateScreenStateBeforeSearch(isNewSearch)

        viewModelScope.launch {
            Timber.d("Starting search: query='%s', isNewSearch=%s", query, isNewSearch)

            val pageToLoad = preparePageToLoad(isNewSearch)
            if (!shouldLoadPage(pageToLoad)) {
                Timber.d("Page $pageToLoad was already loaded, skipping search")
            } else {
                try {
                    val searchParameters = SearchParameters(
                        query = query,
                        page = pageToLoad,
                        filter = getCurrentFilter()
                    )

                    searchUseCase(searchParameters).collect { result ->
                        handleSearchResult(result, pageToLoad, isNewSearch)
                    }
                } catch (e: IOException) {
                    Timber.e(e, "Network error")
                    _screenState.value = SearchVacanciesScreenState.NetworkError(
                        hasActiveFilters = getCurrentHasActiveFilters(_screenState.value)
                    )
                }
            }
        }
    }

    private suspend fun getCurrentFilter(): Filter? {
        return getFilterUseCase().first()
    }

    private fun shouldLoadPage(page: Int): Boolean = !loadedPages.contains(page)

    private fun updateScreenStateBeforeSearch(isNewSearch: Boolean) {
        val hasFilters = getCurrentHasActiveFilters(_screenState.value)
        _screenState.value = if (isNewSearch) {
            SearchVacanciesScreenState.Loading(hasActiveFilters = hasFilters)
        } else {
            SearchVacanciesScreenState.Pagination(hasActiveFilters = hasFilters)
        }
    }

    private fun handleSearchResult(
        result: Result<SearchResult>,
        pageToLoad: Int,
        isNewSearch: Boolean
    ) {
        val hasFilters = getCurrentHasActiveFilters(_screenState.value)

        when (result) {
            is Result.Success -> {
                val searchResult = result.data
                loadedPages.add(pageToLoad)
                currentPage = searchResult.currentPage
                totalPages = searchResult.totalPages

                allVacancies = if (isNewSearch) {
                    searchResult.vacancies
                } else {
                    (allVacancies + searchResult.vacancies).distinctBy { it.id }
                }

                _screenState.value = if (allVacancies.isEmpty()) {
                    SearchVacanciesScreenState.NoResults(hasActiveFilters = hasFilters)
                } else {
                    SearchVacanciesScreenState.Content(
                        searchResult = SearchResult(
                            resultsFound = searchResult.resultsFound,
                            totalPages = totalPages,
                            currentPage = currentPage,
                            vacancies = allVacancies
                        ),
                        hasActiveFilters = hasFilters
                    )
                }
            }

            is Result.NotFound -> {
                _screenState.value = SearchVacanciesScreenState.NoResults(hasActiveFilters = hasFilters)
            }

            is Result.ServerError -> {
                _screenState.value = SearchVacanciesScreenState.ServerError(hasActiveFilters = hasFilters)
            }

            is Result.NoInternet -> {
                _screenState.value = SearchVacanciesScreenState.NetworkError(hasActiveFilters = hasFilters)
            }
        }
    }

    private fun preparePageToLoad(isNewSearch: Boolean) = if (isNewSearch) {
        loadedPages.clear()
        allVacancies = emptyList()
        INITIAL_PAGE // дефолтная страница
    } else {
        currentPage + 1
    }

    companion object {
        // Константы для поиска
        private const val INITIAL_PAGE = 0
    }
}
