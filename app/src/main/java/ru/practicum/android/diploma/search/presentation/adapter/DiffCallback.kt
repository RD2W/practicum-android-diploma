package ru.practicum.android.diploma.search.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when {
            oldItem is ListItem.VacancyItem && newItem is ListItem.VacancyItem ->
                oldItem.vacancy.id == newItem.vacancy.id
            oldItem is ListItem.LoadingItem && newItem is ListItem.LoadingItem -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}
