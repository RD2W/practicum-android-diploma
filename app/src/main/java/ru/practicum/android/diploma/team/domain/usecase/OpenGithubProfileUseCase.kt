package ru.practicum.android.diploma.team.domain.usecase

import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository

/**
 * UseCase для открытия профиля GitHub
 * @property repository Репозиторий для работы с данными о команде
 */
class OpenGithubProfileUseCase(private val repository: DevTeamRepository) {
    /**
     * Вызывает открытие профиля GitHub
     * @param githubLink Ссылка на профиль GitHub
     */
    operator fun invoke(githubLink: String) = repository.openGithubProfile(githubLink)
}
