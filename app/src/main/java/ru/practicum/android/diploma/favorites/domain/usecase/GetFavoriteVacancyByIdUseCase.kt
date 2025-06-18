package ru.practicum.android.diploma.favorites.domain.usecase

import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс для получения конкретной избранной вакансии по ID
 */
interface GetFavoriteVacancyByIdUseCase {
    /**
     * Получает вакансию из избранного по ID
     * @param vacancyId ID вакансии
     * @return VacancyDetails? Найденная вакансия или null если не найдена
     */
    suspend operator fun invoke(vacancyId: String): VacancyDetails?
}
