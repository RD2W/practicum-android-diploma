package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.repository.AreasListGetter
import ru.practicum.android.diploma.region.domain.model.Area

class GetAreasListUseCase(private val areasListGetter: AreasListGetter) {
    fun execute(countryId: String): List<Area> {
        return areasListGetter.getAreasList(countryId)
    }
}
