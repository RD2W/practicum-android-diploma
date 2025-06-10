package ru.practicum.android.diploma.favorites.domain.usecase

import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Use case для добавления вакансии в избранное
 * @property repository Репозиторий для работы с избранными вакансиями
 */
class AddToFavoriteUseCase(
    private val repository: FavoriteVacanciesRepository,
) {
    /**
     * Добавляет вакансию в избранное
     * @param vacancyDetails Вакансия для добавления
     */
    suspend operator fun invoke(vacancyDetails: VacancyDetails) = repository.insertFavoriteVacancy(vacancyDetails)
}
