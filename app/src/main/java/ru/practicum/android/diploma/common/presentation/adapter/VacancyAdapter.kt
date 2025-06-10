package ru.practicum.android.diploma.common.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.databinding.ItemVacancyBinding

/**
 * Адаптер для списка вакансий
 * @property onVacancyClick Лямбда-функция, вызываемая при клике на вакансию
 */
class VacancyAdapter(
    private val onVacancyClick: (Vacancy) -> Unit,
) : ListAdapter<Vacancy, VacancyViewHolder>(VacancyDiffCallback()) {

    /**
     * Создает новый ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyViewHolder(binding)
    }

    /**
     * Привязывает данные вакансии к ViewHolder
     */
    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = getItem(position)
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            onVacancyClick(vacancy)
        }
    }
}
