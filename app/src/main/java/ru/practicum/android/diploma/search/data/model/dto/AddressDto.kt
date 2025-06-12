package ru.practicum.android.diploma.search.data.model.dto

data class AddressDto(
    val city: String,
    val street: String? = null,
    val building: String? = null,
)
