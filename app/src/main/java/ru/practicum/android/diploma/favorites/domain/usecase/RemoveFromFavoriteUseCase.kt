package ru.practicum.android.diploma.favorites.domain.usecase

/**
 * Интерфейс для удаления вакансии из избранного
 */
interface RemoveFromFavoriteUseCase {
    /**
     * Удаляет вакансию из избранного
     * @param vacancyId ID вакансии для удаления
     */
    suspend operator fun invoke(vacancyId: String)
}
