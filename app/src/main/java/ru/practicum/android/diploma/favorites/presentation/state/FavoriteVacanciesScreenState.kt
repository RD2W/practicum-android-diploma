package ru.practicum.android.diploma.favorites.presentation.state

import ru.practicum.android.diploma.common.domain.model.Vacancy

/**
 * Модель состояний UI для экрана "Избранные вакансии".
 * Реализована как sealed class для представления конечного набора возможных состояний экрана.
 *
 * Используется во ViewModel для управления отображением различных UI состояний:
 * - Загрузка данных
 * - Отображение списка вакансий
 * - Пустое состояние (когда нет избранных)
 * - Ошибка загрузки данных
 */
sealed class FavoriteVacanciesScreenState {
    /**
     * Состояние успешной загрузки списка избранных вакансий.
     * @property vacancies Список объектов Vacancy для отображения.
     * Гарантированно не пустой (для пустого списка используется Empty).
     */
    data class Content(val vacancies: List<Vacancy>) : FavoriteVacanciesScreenState()

    /**
     * Состояние загрузки данных.
     * Используется при первоначальной загрузке или обновлении списка.
     */
    data object Loading : FavoriteVacanciesScreenState()

    /**
     * Состояние пустого списка избранных вакансий.
     * Используется, когда в избранном нет ни одной вакансии.
     */
    data object Empty : FavoriteVacanciesScreenState()

    /**
     * Состояние ошибки при загрузке данных.
     * Используется при возникновении любых ошибок получения данных из репозитория.
     */
    data object Error : FavoriteVacanciesScreenState()
}
