package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.Vacancy

/**
 * Интерфейс для получения списка избранных вакансий
 */
interface GetFavoriteVacanciesUseCase {
    /**
     * Получает список избранных вакансий
     * @return Flow<List<Vacancy>> Поток со списком избранных вакансий
     */
    operator fun invoke(): Flow<List<Vacancy>>
}
