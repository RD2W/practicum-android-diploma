package ru.practicum.android.diploma.favorites.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.favorites.domain.usecase.CheckIsFavoriteUseCase

/**
 * Use case для проверки, находится ли вакансия в избранном
 * @property repository Репозиторий для работы с избранными вакансиями
 */
class CheckIsFavoriteUseCaseImpl(
    private val repository: FavoriteVacanciesRepository,
) : CheckIsFavoriteUseCase {
    /**
     * Проверяет, является ли вакансия избранной
     * @param vacancyId ID вакансии для проверки
     * @return Flow<Boolean> Поток с результатом проверки (true - если в избранном)
     */
    override operator fun invoke(vacancyId: String): Flow<Boolean> = repository.isVacancyFavorite(vacancyId)
}
