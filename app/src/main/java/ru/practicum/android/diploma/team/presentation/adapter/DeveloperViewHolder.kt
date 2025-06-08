package ru.practicum.android.diploma.team.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemDeveloperBinding
import ru.practicum.android.diploma.team.domain.model.Developer

/**
 * ViewHolder для отображения элемента разработчика в списке
 * @property binding Binding для доступа к элементам UI
 */
class DeveloperViewHolder(private val binding: ItemDeveloperBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Привязывает данные разработчика к элементам View
     * @param developer Модель разработчика для отображения
     */
    fun bind(developer: Developer) {
        with(binding) {
            // Устанавливаем имя разработчика
            tvDeveloperName.text = developer.fullName

            // Устанавливаем роль разработчика
            tvDeveloperRole.text = developer.role

            // Форматируем строку с технологиями
            tvDeveloperTechnologies.text = binding.root.context.getString(
                R.string.technologies_format,
                developer.technologies.joinToString(", ")
            )

            // Форматируем строку с телеграмом (с проверкой на null)
            tvDeveloperTelegramProfile.text = binding.root.context.getString(
                R.string.telegram_format,
                developer.telegramProfile ?: binding.root.context.getString(R.string.not_specified)
            )
        }

        // Загружаем аватар разработчика с помощью Glide
        Glide.with(binding.root.context)
            .load(developer.avatarUrl)
            .placeholder(R.drawable.ic_placeholder_avatar) // Заглушка на время загрузки
            .fitCenter()
            .centerCrop()
            .into(binding.ivDeveloperAvatar)
    }
}
