package ru.practicum.android.diploma.search.data.model.dto

data class IndustryDto(
    val id: String,
    val name: String,
    val industries: List<IndustryDto>? = null,
)
