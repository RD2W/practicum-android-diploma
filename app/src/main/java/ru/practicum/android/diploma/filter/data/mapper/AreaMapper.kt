package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.region.domain.model.Area
import ru.practicum.android.diploma.search.data.model.dto.AreaDto

fun AreaDto.toArea(): Area {
    return Area(
        id = this.id,
        name = this.name,
        parentId = this.parentId,
        areas = this.areas?.map { it.toArea() },
    )
}
