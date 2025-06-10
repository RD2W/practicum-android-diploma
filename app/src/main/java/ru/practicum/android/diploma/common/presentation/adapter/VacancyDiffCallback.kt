package ru.practicum.android.diploma.common.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.common.domain.model.Vacancy

/**
 * Callback для сравнения элементов списка вакансий
 */
class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    /**
     * Проверяет, что элементы имеют одинаковый ID
     */
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Проверяет, что содержимое элементов одинаковое
     */
    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}
