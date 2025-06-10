package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс репозитория для работы с избранными вакансиями.
 * Определяет контракт для операций с избранными вакансиями.
 */
interface FavoriteVacanciesRepository {
    /**
     * Добавляет вакансию в избранное.
     *
     * @param vacancyDetails Вакансия для добавления
     */
    suspend fun insertFavoriteVacancy(vacancyDetails: VacancyDetails)

    /**
     * Удаляет вакансию из избранного по ID.
     *
     * @param vacancyId ID вакансии для удаления
     */
    suspend fun deleteFavoriteVacancy(vacancyId: String)

    /**
     * Получает конкретную вакансию из избранного по ID.
     *
     * @param vacancyId ID вакансии для поиска
     * @return VacancyDetails? Найденная вакансия или null если не найдена
     */
    suspend fun getFavoriteVacancyById(vacancyId: String): VacancyDetails?

    /**
     * Получает все избранные вакансии.
     *
     * @return Flow<List<Vacancy>> Поток со списком избранных вакансий
     */
    fun getFavoriteVacancies(): Flow<List<Vacancy>>

    /**
     * Проверяет, является ли вакансия избранной.
     *
     * @param vacancyId ID вакансии для проверки
     * @return Flow<Boolean> Поток с результатом проверки
     */
    fun isVacancyFavorite(vacancyId: String): Flow<Boolean>
}
