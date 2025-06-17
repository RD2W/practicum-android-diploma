package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.filter.domain.model.Filter

interface SetFilterRepository {
    fun setFilter(filter: Filter)
}
