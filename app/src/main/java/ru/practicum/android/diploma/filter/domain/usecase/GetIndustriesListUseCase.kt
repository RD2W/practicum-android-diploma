package ru.practicum.android.diploma.filter.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.GetIndustriesListResult
import ru.practicum.android.diploma.filter.domain.repository.IndustriesListGetter

class GetIndustriesListUseCase(private val industriesListGetter: IndustriesListGetter) {
    fun execute(): Flow<GetIndustriesListResult> {
        return industriesListGetter.getIndustriesList()
    }
}
