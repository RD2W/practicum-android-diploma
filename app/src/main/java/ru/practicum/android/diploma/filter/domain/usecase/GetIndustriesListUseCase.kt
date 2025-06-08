package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.repository.IndustriesListGetter
import ru.practicum.android.diploma.region.domain.model.Country

class GetIndustriesListUseCase(private val industriesListGetter: IndustriesListGetter) {
    fun execute(): List<Country> {
        return industriesListGetter.getIndustriesList()
    }
}
