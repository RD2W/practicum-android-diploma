package ru.practicum.android.diploma.vacancy.presentation.state

/**
 * Перечисление возможных типов ошибок при работе с вакансиями.
 *
 * - SERVER_ERROR - Ошибки сервера
 * - NOT_FOUND - Вакансия не найдена (404 Not Found)
 * - NO_INTERNET - Отсутствует интернет-соединение
 */
enum class VacancyErrorType {
    SERVER_ERROR,
    NOT_FOUND,
    NO_INTERNET,
}
