package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для проверки статуса избранного у вакансии
 */
interface CheckIsFavoriteUseCase {
    /**
     * Проверяет, является ли вакансия избранной
     * @param vacancyId ID вакансии для проверки
     * @return Flow<Boolean> Поток с результатом проверки (true - если в избранном)
     */
    operator fun invoke(vacancyId: String): Flow<Boolean>
}
