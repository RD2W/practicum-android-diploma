package ru.practicum.android.diploma.favorites.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.common.constants.AppConstants.DATABASE_VERSION
import ru.practicum.android.diploma.favorites.data.source.local.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.data.source.local.db.entity.FavoriteVacancyEntity

/**
 * Основная база данных приложения для хранения избранных вакансий.
 * Использует Room для работы с SQLite.
 *
 * @property entities Список классов сущностей, которые будут храниться в базе данных
 * @property version Версия базы данных (при изменении схемы нужно увеличивать)
 * @property exportSchema Флаг экспорта схемы (false для отключения)
 */
@Database(
    entities = [FavoriteVacancyEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Получает DAO для работы с избранными вакансиями
     * @return FavoriteVacancyDao интерфейс для доступа к операциям с базой данных
     */
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
