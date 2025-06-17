package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс для переключения статуса избранного
 */
interface ToggleFavoriteStatusUseCase {
    /**
     * Переключает состояние избранного для вакансии
     * @param vacancyDetails Вакансия для изменения статуса
     * @return Flow<Boolean> Новый статус избранного
     */
    suspend operator fun invoke(vacancyDetails: VacancyDetails): Flow<Boolean>
}
