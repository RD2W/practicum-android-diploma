package ru.practicum.android.diploma.search.presentation.state

import ru.practicum.android.diploma.search.domain.model.SearchResult

/**
 * Модель состояний UI для экрана "Поиск вакансии".
 * Реализована как sealed class для представления конечного набора возможных состояний экрана.
 *
 * Используется во ViewModel для управления отображением различных UI состояний:
 * - Инициализация
 * - Загрузка данных
 * - Отображение списка найденных вакансий
 * - Пустое состояние
 * - Ошибка загрузки данных
 */
sealed class SearchVacanciesScreenState {
    /** Флаг, указывающий, что применены активные фильтры */
    abstract val hasActiveFilters: Boolean

    /**
     * Состояние инициализации.
     */
    data class Initial(override val hasActiveFilters: Boolean = false) : SearchVacanciesScreenState()

    /**
     * Состояние загрузки данных.
     * Используется при первоначальной загрузке или обновлении списка.
     */
    data class Loading(override val hasActiveFilters: Boolean = false) : SearchVacanciesScreenState()

    /**
     * Состояние пагинации (подгрузки данных).
     */
    data class Pagination(override val hasActiveFilters: Boolean = false) : SearchVacanciesScreenState()

    /**
     * Состояние успешной загрузки списка найденных вакансий.
     * @param searchResult Список объектов SearchResult для отображения.
     */
    data class Content(
        val searchResult: SearchResult,
        override val hasActiveFilters: Boolean = false,
    ) : SearchVacanciesScreenState()

    /**
     * Состояние пустого списка найденных вакансий.
     * Используется, когда не нашлось ни одной вакансии.
     */
    data class NoResults(override val hasActiveFilters: Boolean = false) : SearchVacanciesScreenState()

    /**
     * Состояние ошибки интернета.
     * Используется, если в момент поиска произошла сетевая ошибка.
     */
    data class NetworkError(override val hasActiveFilters: Boolean = false) : SearchVacanciesScreenState()

    /**
     * Состояние ошибки сервера.
     * Используется, если в момент поиска сервер вернул ошибку.
     */
    data class ServerError(override val hasActiveFilters: Boolean = false) : SearchVacanciesScreenState()
}
