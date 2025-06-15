package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.utils.NetworkUtils
import ru.practicum.android.diploma.common.utils.debounce
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase
import ru.practicum.android.diploma.search.presentation.state.SearchVacanciesScreenState
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

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
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _screenState = MutableStateFlow<SearchVacanciesScreenState>(SearchVacanciesScreenState.Initial)
    val screenState: StateFlow<SearchVacanciesScreenState> = _screenState

    private var currentQuery = ""
    private var currentFilter: Filter? = null
    private var currentPage = 0
    private var totalPages = 1
    private val loadedPages = mutableSetOf<Int>()
    private var allVacancies = emptyList<Vacancy>()

    private val debouncedSearch = debounce<String>(
        delayMillis = AppConstants.SEARCH_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { query ->
        performSearch(query, currentFilter, isNewSearch = true)
    }

    /**
     * Настройка инициализации
     */
    fun onSearchQueryChanged(query: String) {
        currentQuery = query.trim()
        if (currentQuery.isBlank()) {
            _screenState.value = SearchVacanciesScreenState.Initial
            loadedPages.clear()
            allVacancies = emptyList()
        } else {
            debouncedSearch(currentQuery)
            Timber.d("Debounced query received: '%s'", currentQuery)
        }
    }

    /**
     * Настройка изменения фильтров
     */
    fun onFiltersChanged(filter: Filter?) {
        currentFilter = filter
        if (currentQuery.isNotEmpty()) {
            performSearch(currentQuery, filter, isNewSearch = true)
        }
    }

    /**
     * Загрузка следующей страницы
     */
    fun loadNextPage() {
        if (currentPage < totalPages && !loadedPages.contains(currentPage + 1)) {
            performSearch(currentQuery, currentFilter, isNewSearch = false)
        }
    }

    private fun performSearch(query: String, filter: Filter?, isNewSearch: Boolean) {
        if (query.isBlank()) return

        _screenState.value = SearchVacanciesScreenState.Loading

        viewModelScope.launch {
            Timber.d("Starting search: query='%s', filter=%s, isNewSearch=%s", query, filter, isNewSearch)

            val pageToLoad = if (isNewSearch) {
                loadedPages.clear()
                allVacancies = emptyList()
                0 // дефолтная страница
            } else {
                currentPage + 1
            }

            if (loadedPages.contains(pageToLoad)) return@launch

            try {
                val searchParameters = SearchParameters(
                    query = query,
                    page = pageToLoad,
                    filter = filter
                )

                searchUseCase(searchParameters).collect { result ->
                    when (result) {
                        is RequestResult.Success -> {
                            result.searchResult?.let { searchResult ->
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
                            } ?: run {
                                _screenState.value = SearchVacanciesScreenState.ServerError
                            }
                        }
                        is RequestResult.Error -> {
                            handleError(result.errorType ?: HttpURLConnection.HTTP_INTERNAL_ERROR)
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Search error")
                _screenState.value = when (getErrorCodeFromException(e)) {
                    HttpURLConnection.HTTP_UNAVAILABLE,
                    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> SearchVacanciesScreenState.NetworkError
                    else -> SearchVacanciesScreenState.ServerError
                }
            }
        }
    }


    private fun handleError(errorCode: Int) {
        _screenState.value = when {
            errorCode == HttpURLConnection.HTTP_UNAUTHORIZED ->
                SearchVacanciesScreenState.ServerError
            errorCode == HttpURLConnection.HTTP_FORBIDDEN ->
                SearchVacanciesScreenState.ServerError
            errorCode == HttpURLConnection.HTTP_NOT_FOUND ->
                SearchVacanciesScreenState.NoResults
            !networkUtils.isNetworkAvailable() ->  // Используется NetworkUtils
                SearchVacanciesScreenState.NetworkError
            else ->
                SearchVacanciesScreenState.ServerError
        }
    }

    /**
     * Определяет код ошибки на основе перехваченного исключения
     * @param e Исключение, которое нужно обработать
     * @return Код HTTP ошибки для определения типа состояния экрана
     */
    private fun getErrorCodeFromException(e: Exception): Int {
        return when (e) {
            is IOException, is SocketTimeoutException -> HttpURLConnection.HTTP_UNAVAILABLE
            is HttpException -> e.code()
            else -> HttpURLConnection.HTTP_INTERNAL_ERROR
        }
    }
}
