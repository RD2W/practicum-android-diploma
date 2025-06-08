package ru.practicum.android.diploma.team.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * Callback для сравнения элементов списка разработчиков
 */
class DeveloperDiffCallback : DiffUtil.ItemCallback<Developer>() {
    /**
     * Проверяет, что элементы имеют одинаковый ID
     */
    override fun areItemsTheSame(oldItem: Developer, newItem: Developer): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Проверяет, что содержимое элементов одинаковое
     */
    override fun areContentsTheSame(oldItem: Developer, newItem: Developer): Boolean {
        return oldItem == newItem
    }
}
