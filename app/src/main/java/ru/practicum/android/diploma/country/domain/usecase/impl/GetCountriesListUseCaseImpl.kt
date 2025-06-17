package ru.practicum.android.diploma.country.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.country.domain.usecase.GetCountriesListUseCase

class GetCountriesListUseCaseImpl(
    private val repository: FilterRepository,
) : GetCountriesListUseCase {
    override suspend operator fun invoke(): Flow<RequestResult<List<Country>>> = repository.getCountries()
}
