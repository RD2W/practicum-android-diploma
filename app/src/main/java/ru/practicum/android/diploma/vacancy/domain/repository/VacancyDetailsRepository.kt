package ru.practicum.android.diploma.vacancy.domain.repository

import ru.practicum.android.diploma.vacancy.domain.model.Result
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс репозитория для работы с детальной информацией о вакансиях.
 * Предоставляет методы для получения данных о вакансии и шаринга ссылки на вакансию.
 */
interface VacancyDetailsRepository {
    /**
     * Получает детальную информацию о вакансии по её идентификатору.
     *
     * @param vacancyId Уникальный идентификатор вакансии в системе HeadHunter.
     * @return [Result], содержащий либо успешный результат с данными вакансии ([Result.Success]),
     *         либо информацию об ошибке ([Result.ServerError], [Result.NotFound], [Result.NoInternet]).
     *         Возвращаемый тип - [VacancyDetails].
     *
     * @see Result
     * @see VacancyDetails
     */
    suspend fun getVacancyDetails(vacancyId: String): Result<VacancyDetails>

    /**
     * Открывает системный диалог для sharing (поделиться) ссылки на вакансию.
     * Позволяет пользователю выбрать приложение для отправки ссылки.
     *
     * @param titleOfVacancy Название вакансии, которое будет отображаться в заголовке диалога sharing.
     * @param alternateUrl Прямая ссылка на вакансию на сайте HeadHunter.
     *                     Формат: "https://hh.ru/vacancy/{vacancyId}".
     *
     * @throws ActivityNotFoundException если на устройстве не найдено приложение,
     *                                   способное обработать sharing.
     */
    fun shareVacancyLink(titleOfVacancy: String, alternateUrl: String)
}
