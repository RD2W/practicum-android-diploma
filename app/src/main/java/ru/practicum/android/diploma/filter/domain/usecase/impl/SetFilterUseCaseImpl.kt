package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.SetFilterRepository

class SetFilterUseCaseImpl(private val setFilterRepository: SetFilterRepository) {
    fun execute(filter: Filter) {
        setFilterRepository.setFilter(filter)
    }
}
