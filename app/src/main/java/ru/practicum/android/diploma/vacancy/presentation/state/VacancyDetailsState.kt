package ru.practicum.android.diploma.vacancy.presentation.state

import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Sealed-класс, представляющий различные состояния экрана с деталями вакансии.
 * Используется для управления UI в зависимости от текущего состояния загрузки данных.
 */
sealed class VacancyDetailsState {
    /**
     * Состояние загрузки данных о вакансии.
     * Должен отображать индикатор загрузки.
     */
    object Loading : VacancyDetailsState()

    /**
     * Состояние успешной загрузки данных о вакансии.
     *
     * @property vacancy Загруженные данные о вакансии типа [VacancyDetails]
     * @property isFavorite Флаг, указывающий находится ли вакансия в избранном.
     *                     true - в избранном, false - не в избранном.
     */
    data class Content(
        val vacancy: VacancyDetails,
        val isFavorite: Boolean,
    ) : VacancyDetailsState()

    /**
     * Состояние ошибки при загрузке данных о вакансии.
     *
     * @property errorType Тип ошибки из перечисления [VacancyErrorType]
     * @property isFavorite Флаг, указывающий находится ли вакансия в избранном.
     *                     true - в избранном, false - не в избранном.
     *                     По умолчанию false.
     */
    data class Error(
        val errorType: VacancyErrorType,
        val isFavorite: Boolean = false,
    ) : VacancyDetailsState()
}
