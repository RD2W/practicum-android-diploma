package ru.practicum.android.diploma.favorites.domain.usecase.impl

import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.favorites.domain.usecase.AddToFavoriteUseCase
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Use case для добавления вакансии в избранное
 * @property repository Репозиторий для работы с избранными вакансиями
 */
internal class AddToFavoriteUseCaseImpl(
    private val repository: FavoriteVacanciesRepository,
) : AddToFavoriteUseCase {
    /**
     * Добавляет вакансию в избранное
     * @param vacancyDetails Вакансия для добавления
     */
    override suspend operator fun invoke(vacancyDetails: VacancyDetails) = repository.insertFavoriteVacancy(
        vacancyDetails
    )
}
