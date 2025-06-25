package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.model.Filter

interface SetFilterUserInterfaceUseCase {
    fun execute(filter: Filter)
}
