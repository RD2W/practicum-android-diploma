package ru.practicum.android.diploma.team.domain.repository

import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * Интерфейс репозитория для работы с данными о команде разработчиков
 */
interface DevTeamRepository {
    /**
     * Получает список разработчиков команды
     * @return List<Developer> - список разработчиков
     */
    suspend fun getDevTeam(): List<Developer>

    /**
     * Открывает профиль GitHub
     * @param githubLink Ссылка на профиль GitHub
     */
    fun openGithubProfile(githubLink: String)
}
