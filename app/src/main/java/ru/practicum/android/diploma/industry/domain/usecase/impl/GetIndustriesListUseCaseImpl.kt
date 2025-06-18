package ru.practicum.android.diploma.industry.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industry.domain.model.GetIndustriesListResult
import ru.practicum.android.diploma.industry.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.industry.domain.repository.GetIndustriesListRepository

class GetIndustriesListUseCaseImpl(
    private val getIndustriesListRepository: GetIndustriesListRepository
) : GetIndustriesListUseCase {
    override fun execute(): Flow<GetIndustriesListResult> {
        return getIndustriesListRepository.getIndustriesList()
    }
}
