package ru.practicum.android.diploma.filter.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.GetCountriesListResult
import ru.practicum.android.diploma.filter.domain.repository.CountriesListGetter
import ru.practicum.android.diploma.region.domain.model.Country

class GetCountriesListUseCase(private val countriesListGetter: CountriesListGetter) {
    fun execute(): Flow<GetCountriesListResult> {
        return countriesListGetter.getCountriesList()
    }
}
