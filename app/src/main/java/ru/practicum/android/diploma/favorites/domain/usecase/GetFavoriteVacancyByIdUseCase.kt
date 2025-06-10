package ru.practicum.android.diploma.favorites.domain.usecase

import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Use case для получения конкретной избранной вакансии по ID
 * @property repository Репозиторий для работы с избранными вакансиями
 */
class GetFavoriteVacancyByIdUseCase(
    private val repository: FavoriteVacanciesRepository,
) {
    /**
     * Получает вакансию из избранного по ID
     * @param vacancyId ID вакансии
     * @return VacancyDetails? Найденная вакансия или null если не найдена
     */
    suspend operator fun invoke(vacancyId: String): VacancyDetails? = repository.getFavoriteVacancyById(vacancyId)
}
