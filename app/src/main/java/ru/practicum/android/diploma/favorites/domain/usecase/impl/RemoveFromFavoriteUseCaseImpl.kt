package ru.practicum.android.diploma.favorites.domain.usecase.impl

import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.favorites.domain.usecase.RemoveFromFavoriteUseCase

/**
 * Use case для удаления вакансии из избранного
 * @property repository Репозиторий для работы с избранными вакансиями
 */
internal class RemoveFromFavoriteUseCaseImpl(
    private val repository: FavoriteVacanciesRepository,
) : RemoveFromFavoriteUseCase {
    /**
     * Удаляет вакансию из избранного
     * @param vacancyId ID вакансии для удаления
     */
    override suspend operator fun invoke(vacancyId: String) = repository.deleteFavoriteVacancy(vacancyId)
}
