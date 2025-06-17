package ru.practicum.android.diploma.team.domain.usecase

/**
 * Интерфейс для UseCase открытия профиля GitHub
 */
interface OpenGithubProfileUseCase {
    /**
     * Вызывает открытие профиля GitHub
     * @param githubLink Ссылка на профиль GitHub
     */
    operator fun invoke(githubLink: String)
}
