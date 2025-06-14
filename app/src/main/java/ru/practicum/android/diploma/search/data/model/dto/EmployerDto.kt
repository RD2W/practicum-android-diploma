package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    val id: String,
    val name: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto? = null
)
