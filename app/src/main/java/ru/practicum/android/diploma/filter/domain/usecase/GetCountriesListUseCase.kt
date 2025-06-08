package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.repository.CountriesListGetter
import ru.practicum.android.diploma.region.domain.model.Country

class GetCountriesListUseCase(private val countriesListGetter: CountriesListGetter) {
    fun execute(): List<Country> {
        return countriesListGetter.getCountriesList()
    }
}
