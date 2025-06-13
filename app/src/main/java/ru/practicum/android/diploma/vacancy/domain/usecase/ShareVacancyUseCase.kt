package ru.practicum.android.diploma.vacancy.domain.usecase

/**
 * Интерфейс UseCase для шаринга (поделиться) вакансии.
 *
 * @param titleOfVacancy Название вакансии (будет использовано как заголовок для шаринга)
 * @param link Ссылка на вакансию
 */
interface ShareVacancyUseCase {
    operator fun invoke(titleOfVacancy: String, link: String)
}
