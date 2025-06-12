package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository

/**
 * Use case для получения списка избранных вакансий
 * @property repository Репозиторий для работы с избранными вакансиями
 */
class GetFavoriteVacanciesUseCase(
    private val repository: FavoriteVacanciesRepository,
) {
    /**
     * Получает список избранных вакансий
     * @return Flow<List<Vacancy>> Поток со списком избранных вакансий
     */
    operator fun invoke(): Flow<List<Vacancy>> = repository.getFavoriteVacancies()
}
