package ru.practicum.android.diploma.country.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.model.RequestResult
import ru.practicum.android.diploma.region.domain.model.Country

interface GetCountriesUseCase {
    suspend operator fun invoke(): Flow<RequestResult<List<Country>>>
}
