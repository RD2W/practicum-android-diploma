package ru.practicum.android.diploma.team.domain.model

/**
 * Доменная модель разработчика
 * @property id Уникальный идентификатор
 * @property fullName Полное имя
 * @property role Роль в команде
 * @property avatarUrl Ссылка на аватар
 * @property technologies Список технологий
 * @property githubProfile Ссылка на профиль GitHub
 * @property telegramProfile Имя пользователя в Telegram
 */
data class Developer(
    val id: Int,
    val fullName: String,
    val role: String,
    val avatarUrl: String? = null,
    val technologies: List<String> = emptyList(),
    val githubProfile: String? = null,
    val telegramProfile: String? = null,
)
