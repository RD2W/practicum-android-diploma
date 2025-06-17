package ru.practicum.android.diploma.favorites.domain.usecase

import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс для добавления вакансии в избранное
 */
interface AddToFavoriteUseCase {
    /**
     * Добавляет вакансию в избранное
     * @param vacancyDetails Вакансия для добавления
     */
    suspend operator fun invoke(vacancyDetails: VacancyDetails)
}
