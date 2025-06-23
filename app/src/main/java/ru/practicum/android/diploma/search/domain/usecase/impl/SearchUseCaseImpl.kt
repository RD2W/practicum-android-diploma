package ru.practicum.android.diploma.search.domain.usecase.impl

import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase

internal class SearchUseCaseImpl(
    private val repository: SearchRepository,
) : SearchUseCase {
    override suspend operator fun invoke(searchParameters: SearchParameters) =
        repository.searchVacancies(searchParameters)
}
