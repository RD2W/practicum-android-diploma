package ru.practicum.android.diploma.team.data.source.local

import ru.practicum.android.diploma.team.data.model.DeveloperDto
import ru.practicum.android.diploma.team.data.source.DataSource

/**
 * Локальный источник данных, реализующий интерфейс DataSource.
 * Содержит предопределенный список разработчиков команды.
 */
class LocalDataSource : DataSource {
    // Список разработчиков с предопределенными данными
    private val devTeam = listOf(
        DeveloperDto(
            id = 1,
            fullName = "Максим Крутоверцев",
            avatarUrl = AVATAR_MAXIM,
            role = ROLE_ANDROID_DEV,
            technologies = listOf(TECH_JAVA, TECH_KOTLIN, TECH_JETPACK_COMPOSE),
            githubProfile = GITHUB_MAXIM,
            telegramProfile = TELEGRAM_MAXIM
        ),
        DeveloperDto(
            id = 2,
            fullName = "Анастасия Воронцова",
            avatarUrl = AVATAR_ANASTASIA,
            role = ROLE_ANDROID_DEV,
            technologies = listOf(TECH_JAVA, TECH_KOTLIN),
            githubProfile = GITHUB_ANASTASIA,
            telegramProfile = TELEGRAM_ANASTASIA
        ),
        DeveloperDto(
            id = 3,
            fullName = "Анна Медведева",
            avatarUrl = AVATAR_ANNA,
            role = ROLE_ANDROID_DEV,
            technologies = listOf(TECH_JAVA, TECH_KOTLIN),
            githubProfile = GITHUB_ANNA,
            telegramProfile = TELEGRAM_ANNA
        ),
        DeveloperDto(
            id = 4,
            fullName = "Светлана Карпухина",
            avatarUrl = AVATAR_SVETLANA,
            role = ROLE_ANDROID_DEV,
            technologies = listOf(TECH_JAVA, TECH_KOTLIN),
            githubProfile = GITHUB_SVETLANA,
            telegramProfile = TELEGRAM_SVETLANA
        ),
        DeveloperDto(
            id = 5,
            fullName = "Михаил Мищенков",
            avatarUrl = AVATAR_MIKHAIL,
            role = ROLE_ANDROID_DEV,
            technologies = listOf(TECH_JAVA, TECH_KOTLIN),
            githubProfile = GITHUB_MIKHAIL,
            telegramProfile = TELEGRAM_MIKHAIL
        ),
    )

    /**
     * Загружает список разработчиков команды
     * @return List<DeveloperDto> - список разработчиков
     */
    override suspend fun loadDevTeam(): List<DeveloperDto> = devTeam

    companion object {
        // Константы технологий
        const val TECH_JAVA = "Java"
        const val TECH_KOTLIN = "Kotlin"
        const val TECH_JETPACK_COMPOSE = "Jetpack Compose"

        // Константы ролей
        const val ROLE_ANDROID_DEV = "Android Developer"

        // Константы URL аватарок
        const val AVATAR_MAXIM = "https://avatars.githubusercontent.com/u/171777583?v=4"
        const val AVATAR_ANASTASIA = "https://avatars.githubusercontent.com/u/173624285?v=4"
        const val AVATAR_ANNA = "https://avatars.githubusercontent.com/u/140705861?v=4"
        const val AVATAR_SVETLANA = "https://avatars.githubusercontent.com/u/110420215?v=4"
        const val AVATAR_MIKHAIL = "https://avatars.githubusercontent.com/u/148260834?v=4"

        // Константы GitHub профилей
        const val GITHUB_MAXIM = "https://github.com/RD2W"
        const val GITHUB_ANASTASIA = "https://github.com/nastyavorontsova"
        const val GITHUB_ANNA = "https://github.com/Annet-Lovett"
        const val GITHUB_SVETLANA = "https://github.com/onecoffeeplz"
        const val GITHUB_MIKHAIL = "https://github.com/Michel-mihim"

        // Константы Telegram профилей
        const val TELEGRAM_MAXIM = "@rd2w_m"
        const val TELEGRAM_ANASTASIA = "@vorontsovanast"
        const val TELEGRAM_ANNA = "@Annet_Lovett"
        const val TELEGRAM_SVETLANA = "@svkarp"
        const val TELEGRAM_MIKHAIL = "@mihelle"
    }
}
