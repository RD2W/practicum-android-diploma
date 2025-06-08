package ru.practicum.android.diploma.team.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemDeveloperBinding
import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * Адаптер для списка разработчиков
 * @property onDeveloperClick Лямбда-функция, вызываемая при клике на разработчика
 */
class DeveloperAdapter(
    private val onDeveloperClick: (Developer) -> Unit,
) : ListAdapter<Developer, DeveloperViewHolder>(DeveloperDiffCallback()) {

    /**
     * Создает новый ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        val binding = ItemDeveloperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DeveloperViewHolder(binding)
    }

    /**
     * Привязывает данные разработчика к ViewHolder
     */
    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        val developer = getItem(position)
        holder.bind(developer)
        holder.itemView.setOnClickListener {
            onDeveloperClick(developer)
        }
    }
}
