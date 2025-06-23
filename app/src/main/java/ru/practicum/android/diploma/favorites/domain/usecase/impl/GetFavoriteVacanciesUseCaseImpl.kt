package ru.practicum.android.diploma.favorites.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacanciesUseCase

/**
 * Use case для получения списка избранных вакансий
 * @property repository Репозиторий для работы с избранными вакансиями
 */
internal class GetFavoriteVacanciesUseCaseImpl(
    private val repository: FavoriteVacanciesRepository,
) : GetFavoriteVacanciesUseCase {
    /**
     * Получает список избранных вакансий
     * @return Flow<List<Vacancy>> Поток со списком избранных вакансий
     */
    override operator fun invoke(): Flow<List<Vacancy>> = repository.getFavoriteVacancies()
}
