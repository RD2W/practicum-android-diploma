package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.region.domain.model.Area
import ru.practicum.android.diploma.search.data.model.dto.AreaDto

fun AreaDto.toArea(): Area {
    return Area(
        id = this.id,
        parentId = this.parentId,
        name = this.name,
        areas = null,
    )
}
