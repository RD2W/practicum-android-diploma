package ru.practicum.android.diploma.team.domain.usecase

import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * Интерфейс для UseCase получения списка разработчиков команды
 */
interface GetDevTeamUseCase {
    /**
     * Получает список разработчиков команды
     * @return List<Developer> - список разработчиков
     */
    suspend operator fun invoke(): List<Developer>
}
