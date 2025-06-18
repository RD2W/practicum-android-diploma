package ru.practicum.android.diploma.vacancy.domain.usecase

import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Интерфейс UseCase для получения детальной информации о вакансии по ID.
 *
 * @param vacancyId Идентификатор вакансии
 * @return Результат операции в виде sealed класса Result<VacancyDetails>
 */
interface GetVacancyDetailsByIdUseCase {
    suspend operator fun invoke(vacancyId: String): Result<VacancyDetails>
}
