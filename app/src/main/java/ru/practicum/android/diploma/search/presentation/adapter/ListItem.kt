package ru.practicum.android.diploma.search.presentation.adapter

import ru.practicum.android.diploma.common.domain.model.Vacancy

sealed class ListItem {
    data class VacancyItem(val vacancy: Vacancy) : ListItem()
    object LoadingItem : ListItem()
}
