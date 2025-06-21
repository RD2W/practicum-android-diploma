package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.SetFilterRepository
import ru.practicum.android.diploma.filter.domain.usecase.SetFilterUseCase

class SetFilterUseCaseImpl(
    private val setFilterRepository: SetFilterRepository
) : SetFilterUseCase {
    override fun execute(filter: Filter) {
        setFilterRepository.setFilter(filter)
    }
}
