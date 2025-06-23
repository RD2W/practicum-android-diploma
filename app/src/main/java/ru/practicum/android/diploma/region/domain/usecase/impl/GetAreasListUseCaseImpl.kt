package ru.practicum.android.diploma.region.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.region.domain.model.GetAreasListResult
import ru.practicum.android.diploma.region.domain.repository.GetAreasListRepository
import ru.practicum.android.diploma.region.domain.usecase.GetAreasListUseCase

internal class GetAreasListUseCaseImpl(
    private val getAreasListRepository: GetAreasListRepository
) : GetAreasListUseCase {
    override fun execute(countryId: String): Flow<GetAreasListResult> {
        return getAreasListRepository.getAreasList(countryId)
    }
}
