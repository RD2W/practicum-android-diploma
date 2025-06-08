package ru.practicum.android.diploma.team.domain.usecase

import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository

/**
 * UseCase для получения списка разработчиков команды
 * @property repository Репозиторий для работы с данными о команде
 */
class GetDevTeamUseCase(private val repository: DevTeamRepository) {
    /**
     * Получает список разработчиков команды
     * @return List<Developer> - список разработчиков
     */
    suspend operator fun invoke() = repository.getDevTeam()
}
