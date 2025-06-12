package ru.practicum.android.diploma.search.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.adapter.VacancyAdapter
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import kotlin.getValue

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    // Viewmodel
    private val viewModel: SearchViewModel by viewModel()

    // Адаптер для RecyclerView
    private val adapter = VacancyAdapter { vacancy ->
        // Логика, исполняемая по нажатию на элемент списка вакансий
        val action = SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancy.id)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        setupSearchField()
        setupButtonListeners()
        setupRecyclerView()

        // Инициализация состояния фильтров
        updateFilterIcons(false) // По умолчанию фильтры не выбраны
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchField() {
        // Получаем доступ к элементам из layout
        val inputEditText = binding.searchItem.inputEditText
        val searchIcon = binding.searchItem.searchIcon
        val clearIcon = binding.searchItem.clearIcon

        // Обработка изменений текста
        inputEditText.doOnTextChanged { text, _, _, _ ->
            val isEmpty = text.isNullOrEmpty()
            searchIcon.isVisible = isEmpty
            clearIcon.isVisible = !isEmpty
        }

        // Обработка клика по иконке очистки
        clearIcon.setOnClickListener {
            inputEditText.text.clear()
            // Возвращаем фокус на поле ввода после очистки
            inputEditText.requestFocus()
        }
    }

    /**
     * Обработка клика по кнопкам
     */
    private fun setupButtonListeners() {
        binding.icFilterOff.setOnClickListener { navigateToFilters() }
        binding.icFilterOn.setOnClickListener { navigateToFilters() }

        // Обработка первой кнопки (на экран вакансии - временная кнопка)
        binding.firstButton.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToVacancyFragment("your_vacancy_id_here")
            )
        }
    }

    /**
     * Переход на экран фильтры
     */
    private fun navigateToFilters() {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToFilterFragment()
        )
    }

    /**
     * Обработка состояние иконки фильтров (выбраны или нет)
     */
    private fun updateFilterIcons(hasFilters: Boolean) {
        binding.icFilterOff.isVisible = !hasFilters
        binding.icFilterOn.isVisible = hasFilters
    }

    /**
     * Настраивает RecyclerView с адаптером
     */
    private fun setupRecyclerView() {
        binding.rvSearchVacancies.adapter = adapter
    }
}
