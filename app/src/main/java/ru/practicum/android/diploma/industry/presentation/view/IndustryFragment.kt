package ru.practicum.android.diploma.industry.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.industry.presentation.viewmodel.IndustryViewModel
import kotlin.getValue

class IndustryFragment : Fragment(R.layout.fragment_industry) {

    private var _binding: FragmentIndustryBinding? = null
    private val binding: FragmentIndustryBinding
        get() = requireNotNull(_binding) { "Binding is null!" }

    private val viewModel: IndustryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIndustryBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
