package ru.practicum.android.diploma.favorites.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.data.source.local.db.entity.FavoriteVacancyEntity

/**
 * Data Access Object (DAO) для работы с избранными вакансиями.
 * Содержит методы для основных CRUD операций.
 */
@Dao
interface FavoriteVacancyDao {

    /**
     * Добавляет вакансию в избранное.
     * При конфликте (если вакансия уже существует) заменяет существующую запись.
     *
     * @param vacancy Сущность вакансии для сохранения
     */
    @Insert(FavoriteVacancyEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVacancy(vacancy: FavoriteVacancyEntity)

    /**
     * Удаляет вакансию из избранного по ID.
     *
     * @param vacancyId ID вакансии для удаления
     */
    @Query("DELETE FROM favorite_vacancies WHERE vacancy_id = :vacancyId")
    suspend fun deleteFavoriteVacancyById(vacancyId: String)

    /**
     * Получает вакансию из избранного по ID.
     *
     * @param vacancyId ID вакансии для поиска
     * @return FavoriteVacancyEntity? Найденная вакансия или null если не найдена
     */
    @Query("SELECT * FROM favorite_vacancies WHERE vacancy_id = :vacancyId")
    suspend fun getFavoriteVacancyById(vacancyId: String): FavoriteVacancyEntity?

    /**
     * Получает все избранные вакансии, отсортированные по дате добавления (новые сначала).
     *
     * @return Flow<List<FavoriteVacancyEntity>> Поток со списком избранных вакансий
     */
    @Query("SELECT * FROM favorite_vacancies ORDER BY added_timestamp DESC")
    fun getFavoriteVacancies(): Flow<List<FavoriteVacancyEntity>>

    /**
     * Проверяет, является ли вакансия избранной.
     *
     * @param vacancyId ID вакансии для проверки
     * @return Flow<Boolean> Поток, эмитирующий true если вакансия в избранном, false если нет
     */
    @Query("SELECT COUNT(*) FROM favorite_vacancies WHERE vacancy_id = :vacancyId")
    fun isVacancyFavorite(vacancyId: String): Flow<Boolean>
}
