package ru.practicum.android.diploma.country.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.country.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.country.domain.repository.CountriesListGetter

class GetCountriesListUseCase(private val countriesListGetter: CountriesListGetter) {
    fun execute(): Flow<GetCountriesListResult> {
        return countriesListGetter.getCountriesList()
    }
}
