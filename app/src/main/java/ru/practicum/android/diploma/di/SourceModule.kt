package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.common.constants.AppConstants.BASE_URL
import ru.practicum.android.diploma.common.constants.AppConstants.DATABASE_NAME
import ru.practicum.android.diploma.common.constants.AppConstants.SHARED_PREFERENCES
import ru.practicum.android.diploma.favorites.data.source.local.db.AppDatabase
import ru.practicum.android.diploma.search.data.source.remote.HHApiHeadersInterceptor
import ru.practicum.android.diploma.search.data.source.remote.HHApiService
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient
import ru.practicum.android.diploma.team.data.source.DataSource
import ru.practicum.android.diploma.team.data.source.local.LocalDataSource

/**
 * Модуль зависимостей приложения, предоставляющий:
 * - Работу с сетью (Retrofit, OkHttp)
 * - Локальное хранилище (SharedPreferences, Room Database)
 * - Конвертацию данных (Gson)
 */
val sourceModule = module {
    // Настройки SharedPreferences для хранения простых данных
    single {
        androidApplication().getSharedPreferences(
            SHARED_PREFERENCES, // Имя файла настроек
            Context.MODE_PRIVATE // Режим доступа (только для этого приложения)
        )
    }

    /**
     * Настройка Room Database
     *
     * Создает экземпляр базы данных с именем "app_database.db"
     * Включает автоматическую миграцию при изменении схемы
     *
     * @return Экземпляр AppDatabase
     */
    single {
        Room.databaseBuilder(
            get(), // Контекст приложения
            AppDatabase::class.java, // Класс базы данных
            DATABASE_NAME, // Имя файла базы данных
        ).build()
    }

    /**
     * DAO (Data Access Object) для работы с избранными вакансиями
     *
     * Предоставляет методы для CRUD операций:
     * - Добавление/удаление вакансий
     * - Получение списка избранного
     * - Проверка наличия вакансии в избранном
     *
     * @return Экземпляр FavoriteVacancyDao
     */
    single { get<AppDatabase>().favoriteVacancyDao() }

    // HTTP-клиент с кастомным интерцептором для добавления заголовков
    single {
        OkHttpClient.Builder()
            .addInterceptor(HHApiHeadersInterceptor()) // Добавляем интерцептор для API hh.ru
            .build()
    }

    // Retrofit-клиент для работы с API hh.ru
    single<HHApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Базовый URL API
            .client(get<OkHttpClient>()) // Используем настроенный OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер JSON в объекты
            .build()
            .create(HHApiService::class.java) // Создаем реализацию API-интерфейса
    }

    // Gson для сериализации/десериализации JSON
    singleOf(::Gson)

    // Сетевой клиент приложения (реализация через Retrofit)
    singleOf(::RetrofitClient) { bind<NetworkClient>() }

    // Локальное хранилище данных о команде разработчиков
    factoryOf(::LocalDataSource) { bind<DataSource>() }
}
