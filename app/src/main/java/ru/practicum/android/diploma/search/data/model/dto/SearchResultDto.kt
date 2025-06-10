package ru.practicum.android.diploma.search.data.model.dto

import com.google.gson.annotations.SerializedName

data class SearchResultDto(
    val items: List<VacancyDto>, // Список вакансий
    val found: Int, // Найдено результатов
    val page: Int, // Номер страницы
    val pages: Int, // Всего страниц
    @SerializedName("per_page")
    val perPage: Int, // Результатов на странице
)
