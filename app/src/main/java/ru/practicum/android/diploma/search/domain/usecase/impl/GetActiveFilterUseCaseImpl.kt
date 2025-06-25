package ru.practicum.android.diploma.search.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.search.domain.usecase.GetActiveFilterUseCase

internal class GetActiveFilterUseCaseImpl(
    private val repository: SearchRepository,
) : GetActiveFilterUseCase {
    override fun invoke(): Flow<Filter?> = repository.getFilter()
}
