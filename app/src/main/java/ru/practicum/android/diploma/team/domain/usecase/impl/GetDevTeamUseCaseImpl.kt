package ru.practicum.android.diploma.team.domain.usecase.impl

import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase

/**
 * UseCase для получения списка разработчиков команды
 * @property repository Репозиторий для работы с данными о команде
 */
internal class GetDevTeamUseCaseImpl(
    private val repository: DevTeamRepository,
) : GetDevTeamUseCase {
    /**
     * Получает список разработчиков команды
     * @return List<Developer> - список разработчиков
     */
    override suspend operator fun invoke() = repository.getDevTeam()
}
