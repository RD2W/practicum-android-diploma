package ru.practicum.android.diploma.region.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.region.domain.model.GetAreasListResult
import ru.practicum.android.diploma.region.domain.repository.AreasListGetter

class GetAreasListUseCase(private val areasListGetter: AreasListGetter) {
    fun execute(countryId: String): Flow<GetAreasListResult> {
        return areasListGetter.getAreasList(countryId)
    }
}
