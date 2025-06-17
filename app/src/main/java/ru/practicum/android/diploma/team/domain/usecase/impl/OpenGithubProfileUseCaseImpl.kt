package ru.practicum.android.diploma.team.domain.usecase.impl

import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase

/**
 * UseCase для открытия профиля GitHub
 * @property repository Репозиторий для работы с данными о команде
 */
class OpenGithubProfileUseCaseImpl(
    private val repository: DevTeamRepository,
) : OpenGithubProfileUseCase {
    /**
     * Вызывает открытие профиля GitHub
     * @param githubLink Ссылка на профиль GitHub
     */
    override operator fun invoke(githubLink: String) = repository.openGithubProfile(githubLink)
}
