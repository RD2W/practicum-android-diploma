package ru.practicum.android.diploma.common.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.databinding.ItemVacancyBinding

/**
 * ViewHolder для отображения элемента вакансии в списке
 * @property binding Binding для доступа к элементам UI
 */
class VacancyViewHolder(private val binding: ItemVacancyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Привязывает данные вакансии к элементам View
     * @param vacancy Модель вакансии для отображения
     */
    fun bind(vacancy: Vacancy) {
        with(binding) {
            // Устанавливаем название вакансии
            tvVacancyName.text = vacancy.titleOfVacancy

            // Устанавливаем название компании
            tvCompanyName.text = vacancy.employerName

            // Устанавливаем сумму зарплаты
            tvVacancySalary.text =
                vacancy.salary ?: binding.root.resources.getString(R.string.vacancy_salary_not_specified)
        }

        // Загружаем логотип компании с помощью Glide
        Glide.with(binding.root.context)
            .load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.ic_vacancy_placeholder) // Заглушка на время загрузки
            .fitCenter()
            .into(binding.ivCompanyLogo)
    }
}
