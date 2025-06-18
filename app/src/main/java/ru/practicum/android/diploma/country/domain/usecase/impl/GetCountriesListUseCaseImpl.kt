package ru.practicum.android.diploma.country.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.country.domain.repository.GetCountriesListRepository
import ru.practicum.android.diploma.country.domain.usecase.GetCountriesListUseCase

class GetCountriesListUseCaseImpl(
    private val getCountriesListRepository: GetCountriesListRepository
) : GetCountriesListUseCase {
    override fun execute(): Flow<GetCountriesListResult> {
        return getCountriesListRepository.getCountriesList()
    }
}
