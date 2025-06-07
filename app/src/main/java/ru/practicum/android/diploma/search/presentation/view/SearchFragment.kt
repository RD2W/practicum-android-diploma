package ru.practicum.android.diploma.search.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import kotlin.getValue

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtonListeners() {
        // Обработка первой кнопки
        binding.firstButton.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToVacancyFragment("your_vacancy_id_here")
            )
        }

        // Обработка второй кнопки
        binding.secondButton.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToFilterFragment()
            )
        }
    }
}
