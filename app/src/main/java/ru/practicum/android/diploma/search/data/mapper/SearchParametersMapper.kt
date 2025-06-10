package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.common.constants.AppConstants.PAGE_SIZE
import ru.practicum.android.diploma.search.domain.model.SearchParameters

fun SearchParameters.toMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["text"] = query
    map["page"] = page.toString()
    map["per_page"] = PAGE_SIZE.toString()

    return map
}
