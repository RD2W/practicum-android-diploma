package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.utils.debounce
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
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
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow<SearchVacanciesScreenState>(SearchVacanciesScreenState.Initial)
    val screenState: StateFlow<SearchVacanciesScreenState> = _screenState

    private var lastQuery = ""
    private var currentFilter: Filter? = null
    private var currentPage = 0
    private var totalPages = 1
    private val loadedPages = mutableSetOf<Int>()
    private var allVacancies = emptyList<Vacancy>()
    private var isNewSearch : Boolean = true

    /**
     * Дебаунс
     */
    private val debouncedSearch = debounce<String>(
        delayMillis = AppConstants.SEARCH_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { query ->
        performSearch(query, currentFilter, isNewSearch = isNewSearch)
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
                _screenState.value = SearchVacanciesScreenState.Initial
                loadedPages.clear()
                allVacancies = emptyList()
            }
            // Запускаем поиск с debounce
            else -> {
                Timber.d("New search query: '$trimmedQuery'")
                debouncedSearch(trimmedQuery,)
            }
        }
    }

    /**
     * Настройка изменения фильтров
     */
    fun onFiltersChanged(filter: Filter?) {
        currentFilter = filter
        if (lastQuery.isNotEmpty()) {
            performSearch(lastQuery, filter, isNewSearch)
        }
    }

    /**
     * Загрузка следующей страницы
     */
    fun loadNextPage() {
        if (currentPage < totalPages && !loadedPages.contains(currentPage + 1)) {
            performSearch(lastQuery, currentFilter, isNewSearch = false)
        }
    }

    private fun performSearch(query: String, filter: Filter?, isNewSearch: Boolean) {
        if (query.isBlank()) return

        updateScreenStateBeforeSearch(isNewSearch)

        viewModelScope.launch {
            Timber.d("Starting search: query='%s', filter=%s, isNewSearch=%s", query, filter, isNewSearch)

            val pageToLoad = preparePageToLoad(isNewSearch)
            if (!shouldLoadPage(pageToLoad)) {
                Timber.d("Page $pageToLoad was already loaded, skipping search")
            } else {
                try {
                    val searchParameters = SearchParameters(
                        query = query,
                        page = pageToLoad,
                        filter = filter
                    )

                    searchUseCase(searchParameters).collect { result ->
                        handleSearchResult(result, pageToLoad, isNewSearch)
                    }
                } catch (e: IOException) {
                    Timber.e(e, "Network error")
                    _screenState.value = SearchVacanciesScreenState.NetworkError
                }
            }
        }
    }

    private fun shouldLoadPage(page: Int): Boolean = !loadedPages.contains(page)

    private fun updateScreenStateBeforeSearch(isNewSearch: Boolean) {
        if (isNewSearch) {
            _screenState.value = SearchVacanciesScreenState.Loading
        } else {
            _screenState.value = SearchVacanciesScreenState.Pagination
        }
    }

    private fun handleSearchResult(
        result: Result<SearchResult>,
        pageToLoad: Int,
        isNewSearch: Boolean
    ) {
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
                    SearchVacanciesScreenState.NoResults
                } else {
                    SearchVacanciesScreenState.Content(
                        searchResult = SearchResult(
                            resultsFound = searchResult.resultsFound,
                            totalPages = totalPages,
                            currentPage = currentPage,
                            vacancies = allVacancies
                        )
                    )

                }
            }

            is Result.NotFound -> {
                _screenState.value = SearchVacanciesScreenState.NoResults
            }

            is Result.ServerError -> {
                Timber.d("Server error: ${result.error}")
                _screenState.value = SearchVacanciesScreenState.ServerError
            }

            is Result.NoInternet -> {
                _screenState.value = SearchVacanciesScreenState.NetworkError
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
