package ru.practicum.android.diploma.favorites.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.favorites.data.mapper.toFavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.data.mapper.toVacancyDetails
import ru.practicum.android.diploma.favorites.data.mapper.toVacancyList
import ru.practicum.android.diploma.favorites.data.source.local.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Реализация репозитория для работы с избранными вакансиями.
 * Обеспечивает преобразование данных между слоями и делегирует операции DAO.
 *
 * @property favoriteVacancyDao DAO для доступа к базе данных избранных вакансий
 */
class FavoriteVacanciesRepositoryImpl(
    private val favoriteVacancyDao: FavoriteVacancyDao,
) : FavoriteVacanciesRepository {

    /**
     * Добавляет вакансию в избранное.
     * Преобразует доменную модель VacancyDetails в сущность FavoriteVacancyEntity перед сохранением.
     *
     * @param vacancyDetails Вакансия для добавления в избранное
     */
    override suspend fun insertFavoriteVacancy(vacancyDetails: VacancyDetails) {
        favoriteVacancyDao.insertFavoriteVacancy(vacancyDetails.toFavoriteVacancyEntity())
    }

    /**
     * Удаляет вакансию из избранного по ID.
     *
     * @param vacancyId ID вакансии для удаления
     */
    override suspend fun deleteFavoriteVacancy(vacancyId: String) {
        favoriteVacancyDao.deleteFavoriteVacancyById(vacancyId)
    }

    /**
     * Получает все избранные вакансии.
     * Преобразует список сущностей в список доменных моделей.
     *
     * @return Flow<List<Vacancy>> Поток со списком избранных вакансий
     */
    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return favoriteVacancyDao.getFavoriteVacancies().map { favouriteVacanciesList ->
            favouriteVacanciesList.toVacancyList()
        }
    }

    /**
     * Получает конкретную вакансию из избранного по ID.
     *
     * @param vacancyId ID вакансии для поиска
     * @return VacancyDetails? Найденная вакансия или null если не найдена
     */
    override suspend fun getFavoriteVacancyById(vacancyId: String) =
        favoriteVacancyDao.getFavoriteVacancyById(vacancyId)?.toVacancyDetails()

    /**
     * Проверяет, является ли вакансия избранной.
     *
     * @param vacancyId ID вакансии для проверки
     * @return Flow<Boolean> Поток с результатом проверки
     */
    override fun isVacancyFavorite(vacancyId: String): Flow<Boolean> =
        favoriteVacancyDao.isVacancyFavorite(vacancyId)
}
