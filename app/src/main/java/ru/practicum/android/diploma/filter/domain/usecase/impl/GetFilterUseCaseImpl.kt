package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.GetFilterRepository
import ru.practicum.android.diploma.filter.domain.usecase.GetFilterUseCase

class GetFilterUseCaseImpl(
    private val getFilterRepository: GetFilterRepository
) : GetFilterUseCase {
    override fun execute(): Filter? {
        return getFilterRepository.getFilter()
    }
}
