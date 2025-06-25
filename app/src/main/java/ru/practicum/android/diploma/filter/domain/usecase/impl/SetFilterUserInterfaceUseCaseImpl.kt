package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.SetFilterRepository
import ru.practicum.android.diploma.filter.domain.repository.SetFilterUserInterfaceRepository
import ru.practicum.android.diploma.filter.domain.usecase.SetFilterUserInterfaceUseCase

class SetFilterUserInterfaceUseCaseImpl(
    private val setFilterUserInterfaceRepository: SetFilterUserInterfaceRepository
) : SetFilterUserInterfaceUseCase {
    override fun execute(filter: Filter) {

    }
}
