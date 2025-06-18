package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class AreaDto(
    val id: String,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val areas: List<AreaDto>? = null,
)
