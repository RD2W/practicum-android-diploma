package ru.practicum.android.diploma.team.data.source

import ru.practicum.android.diploma.team.data.model.DeveloperDto

/**
 * Интерфейс источника данных для работы с информацией о команде разработчиков
 */
interface DataSource {
    /**
     * Загружает список разработчиков команды
     * @return List<DeveloperDto> - список разработчиков в DTO модели
     */
    suspend fun loadDevTeam(): List<DeveloperDto>
}
