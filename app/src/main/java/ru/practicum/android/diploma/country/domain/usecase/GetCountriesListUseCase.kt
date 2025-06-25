package ru.practicum.android.diploma.country.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult

interface GetCountriesListUseCase {
    fun execute(): Flow<GetCountriesListResult>
}
