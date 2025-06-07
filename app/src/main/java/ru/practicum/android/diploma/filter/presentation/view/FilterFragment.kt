package ru.practicum.android.diploma.filter.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import kotlin.getValue

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: FilterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterBinding.bind(view)
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtonListeners() {
        // Обработка первой кнопки - переход к выбору страны
        binding.firstButton.setOnClickListener {
            findNavController().navigate(
                FilterFragmentDirections.actionFilterFragmentToIndustryFragment()
            )
        }

        // Обработка второй кнопки - переход к выбору региона
        binding.secondButton.setOnClickListener {
            findNavController().navigate(
                FilterFragmentDirections.actionFilterFragmentToWorkplaceFragment()
            )
        }
    }
}
