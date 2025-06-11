package ru.practicum.android.diploma.vacancy.domain.usecase

import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class GetVacancyDetailsUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(vacancyId: String) = repository.getVacancyDetails(vacancyId)
}
