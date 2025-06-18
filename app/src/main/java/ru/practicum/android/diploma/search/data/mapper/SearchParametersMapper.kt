package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.constants.AppConstants.PAGE_SIZE
import ru.practicum.android.diploma.search.domain.model.SearchParameters

fun SearchParameters.toMap(): Map<String, String> {
    return buildMap {
        put("text", query)
        put("page", page.toString())
        put("per_page", PAGE_SIZE.toString())
    }
}
