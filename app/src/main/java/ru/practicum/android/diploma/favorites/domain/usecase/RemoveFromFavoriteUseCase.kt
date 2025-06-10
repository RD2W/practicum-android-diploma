package ru.practicum.android.diploma.favorites.domain.usecase

import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository

/**
 * Use case для удаления вакансии из избранного
 * @property repository Репозиторий для работы с избранными вакансиями
 */
class RemoveFromFavoriteUseCase(
    private val repository: FavoriteVacanciesRepository,
) {
    /**
     * Удаляет вакансию из избранного
     * @param vacancyId ID вакансии для удаления
     */
    suspend operator fun invoke(vacancyId: String) = repository.deleteFavoriteVacancy(vacancyId)
}
