package ru.practicum.android.diploma.country.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.country.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.region.domain.model.Country

class GetCountriesUseCaseImpl(
    private val repository: FilterRepository,
) : GetCountriesUseCase {
    override suspend operator fun invoke(): Flow<RequestResult<List<Country>>> = repository.getCountries()
}
