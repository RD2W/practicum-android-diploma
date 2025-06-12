package ru.practicum.android.diploma.team.data.mapper

import ru.practicum.android.diploma.team.data.model.DeveloperDto
import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * Функция-расширение для преобразования DeveloperDto в Developer
 * @return Developer - доменная модель разработчика
 */
fun DeveloperDto.toDeveloper() = Developer(
    id = this.id ?: 0,
    fullName = this.fullName.orEmpty(),
    role = this.role.orEmpty(),
    avatarUrl = this.avatarUrl,
    technologies = this.technologies,
    githubProfile = this.githubProfile,
    telegramProfile = this.telegramProfile,
)
