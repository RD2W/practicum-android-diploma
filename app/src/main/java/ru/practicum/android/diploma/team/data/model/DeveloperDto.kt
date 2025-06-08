package ru.practicum.android.diploma.team.data.model

/**
 * DTO модель разработчика для слоя данных
 * @property id Уникальный идентификатор (может быть null)
 * @property fullName Полное имя (может быть null)
 * @property avatarUrl URL аватара или null, если нет фото
 * @property role Роль в команде (может быть null)
 * @property technologies Список технологий (по умолчанию пустой)
 * @property githubProfile Ссылка на профиль GitHub (может быть null)
 * @property telegramProfile Имя пользователя в Telegram (может быть null)
 */
data class DeveloperDto(
    val id: Int?,
    val fullName: String?,
    val avatarUrl: String? = null, // URL или null, если нет фото
    val role: String? = null,
    val technologies: List<String> = emptyList(),
    val githubProfile: String? = null,
    val telegramProfile: String? = null,
)
