package ru.practicum.android.diploma.filter.domain.usecase.impl

import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.GetFilterUserInterfaceRepository
import ru.practicum.android.diploma.filter.domain.usecase.GetFilterUserInterfaceUseCase

class GetFilterUserInterfaceUseCaseImpl(
    private val getFilterUserInterfaceRepository: GetFilterUserInterfaceRepository
) : GetFilterUserInterfaceUseCase {
    override fun execute(): Filter? {
        return getFilterUserInterfaceRepository.getFilter()
    }
}
