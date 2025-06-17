package ru.practicum.android.diploma.filter.domain.usecase

import ru.practicum.android.diploma.filter.domain.model.Filter

interface SetFilterUseCase {
    fun execute(filter: Filter)
}
