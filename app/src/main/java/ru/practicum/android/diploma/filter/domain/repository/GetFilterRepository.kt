package ru.practicum.android.diploma.filter.domain.repository

import ru.practicum.android.diploma.filter.domain.model.Filter

interface GetFilterRepository {
    fun getFilter(): Filter?
}
