package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository

/**
 * Use case для проверки, находится ли вакансия в избранном
 * @property repository Репозиторий для работы с избранными вакансиями
 */
class CheckIsFavoriteUseCase(
    private val repository: FavoriteVacanciesRepository,
) {
    /**
     * Проверяет, является ли вакансия избранной
     * @param vacancyId ID вакансии для проверки
     * @return Flow<Boolean> Поток с результатом проверки (true - если в избранном)
     */
    operator fun invoke(vacancyId: String): Flow<Boolean> = repository.isVacancyFavorite(vacancyId)
}
