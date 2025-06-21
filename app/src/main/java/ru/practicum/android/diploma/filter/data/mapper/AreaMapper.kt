package ru.practicum.android.diploma.filter.data.mapper

import ru.practicum.android.diploma.region.domain.model.AreaRegion
import ru.practicum.android.diploma.search.data.model.dto.AreaDto

fun AreaDto.toAreaRegion(): AreaRegion {
    return AreaRegion(
        id = this.id,
        name = this.name
    )
}
