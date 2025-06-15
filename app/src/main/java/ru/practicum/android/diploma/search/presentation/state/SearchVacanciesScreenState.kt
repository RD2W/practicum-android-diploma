package ru.practicum.android.diploma.search.presentation.state

import ru.practicum.android.diploma.common.domain.model.Vacancy
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
    /**
     * Состояние инициализации
     */
    data object Initial : SearchVacanciesScreenState()

    /**
     * Состояние загрузки данных.
     * Используется при первоначальной загрузке или обновлении списка.
     */
    data object Loading : SearchVacanciesScreenState()

    /**
     * Состояние успешной загрузки списка найденных вакансий.
     * @property vacancies Список объектов Vacancy для отображения.
     */
    data class Content(val searchResult: SearchResult) : SearchVacanciesScreenState()

    /**
     * Состояние пустого списка найденных вакансий.
     * Используется, когда не нашлась ни одна вакансия
     */
    data object NoResults : SearchVacanciesScreenState()

    /**
     * Состояние, если происходит ошибка интернета
     * Используется, если в в момент поиска произошла ошибка интернета
     */
    data object NetworkError : SearchVacanciesScreenState()

    /**
     * Состояние, если происходит ошибка сервера
     * Используется, если в в момент поиска произошла ошибка сервера
     */
    data object ServerError : SearchVacanciesScreenState()
}
