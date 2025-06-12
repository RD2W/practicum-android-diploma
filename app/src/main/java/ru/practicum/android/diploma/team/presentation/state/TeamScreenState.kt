package ru.practicum.android.diploma.team.presentation.state

import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * Состояния экрана команды разработчиков
 */
sealed class TeamScreenState {
    data class Content(val developers: List<Developer>) : TeamScreenState() // Состояние с данными
    data object Loading : TeamScreenState() // Состояние загрузки
    data object Error : TeamScreenState() // Состояние ошибки
}
