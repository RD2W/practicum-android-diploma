package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.search.data.model.dto.SearchResultDto
import ru.practicum.android.diploma.search.domain.model.SearchResult

fun SearchResultDto.toSearchResult() = SearchResult(
    resultsFound = this.found,
    totalPages = this.pages,
    currentPage = this.page,
    vacancies = this.items.map { it.toVacancy() },
)
