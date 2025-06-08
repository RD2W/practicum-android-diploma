package ru.practicum.android.diploma.team.data.repository

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import ru.practicum.android.diploma.team.data.mapper.toDeveloper
import ru.practicum.android.diploma.team.data.source.DataSource
import ru.practicum.android.diploma.team.domain.model.Developer
import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository

/**
 * Реализация репозитория для работы с данными о команде разработчиков.
 * @property context Контекст приложения для выполнения Intent
 * @property dataSource Источник данных (локальный или удаленный)
 */
class DevTeamRepositoryImpl(
    private val context: Context,
    private val dataSource: DataSource,
) : DevTeamRepository {
    /**
     * Получает список разработчиков команды
     * @return List<Developer> - список разработчиков в доменной модели
     */
    override suspend fun getDevTeam(): List<Developer> {
        val developersDto = dataSource.loadDevTeam()
        return developersDto.map { it.toDeveloper() }
    }

    /**
     * Открывает профиль GitHub в браузере или приложении GitHub
     * @param githubLink Ссылка на профиль GitHub
     */
    override fun openGithubProfile(githubLink: String) {
        val agreementIntent = Intent(
            Intent.ACTION_VIEW,
            githubLink.toUri(),
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(agreementIntent)
    }
}
