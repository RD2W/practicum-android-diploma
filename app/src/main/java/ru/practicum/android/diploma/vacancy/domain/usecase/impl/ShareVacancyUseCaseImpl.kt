package ru.practicum.android.diploma.vacancy.domain.usecase.impl

import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.usecase.ShareVacancyUseCase

internal class ShareVacancyUseCaseImpl(
    private val repository: VacancyDetailsRepository,
) : ShareVacancyUseCase {
    override operator fun invoke(titleOfVacancy: String, link: String) =
        repository.shareVacancyLink(titleOfVacancy, link)
}
