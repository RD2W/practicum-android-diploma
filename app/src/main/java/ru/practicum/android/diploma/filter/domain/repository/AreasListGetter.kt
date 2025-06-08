package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.region.domain.model.Area

interface AreasListGetter {
    fun getAreasList(countryId: String): List<Area>
}
