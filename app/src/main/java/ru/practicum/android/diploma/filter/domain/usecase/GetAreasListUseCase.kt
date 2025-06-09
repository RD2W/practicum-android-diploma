package ru.practicum.android.diploma.filter.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.GetAreasListResult
import ru.practicum.android.diploma.filter.domain.repository.AreasListGetter

class GetAreasListUseCase(private val areasListGetter: AreasListGetter) {
    fun execute(countryId: String): Flow<GetAreasListResult> {
        return areasListGetter.getAreasList(countryId)
    }
}
