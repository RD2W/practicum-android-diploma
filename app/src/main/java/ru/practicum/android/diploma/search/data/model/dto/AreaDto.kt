package ru.practicum.android.diploma.search.data.model.dto

data class AreaDto(
    val id: String,
    val name: String,
    val areas: List<AreaDto>? = null,
)
