package ru.practicum.android.diploma.vacancy.domain.usecase.impl

import ru.practicum.android.diploma.vacancy.domain.model.Result
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.usecase.GetVacancyDetailsByIdUseCase

class GetVacancyDetailsByIdUseCaseImpl(
    private val repository: VacancyDetailsRepository,
) : GetVacancyDetailsByIdUseCase {
    override suspend operator fun invoke(vacancyId: String): Result<VacancyDetails> =
        repository.getVacancyDetails(vacancyId)
}
