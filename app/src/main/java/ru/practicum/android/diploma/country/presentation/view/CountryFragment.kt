package ru.practicum.android.diploma.country.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.country.presentation.state.CountryFragmentState
import ru.practicum.android.diploma.country.presentation.viewmodel.CountryViewModel
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import kotlin.getValue

class CountryFragment : Fragment(R.layout.fragment_country) {

    private var _binding: FragmentCountryBinding? = null
    private val binding: FragmentCountryBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: CountryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCountryBinding.bind(view)

        setupLiveDataObservers()
        viewModel.getCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLiveDataObservers() {
        viewModel.observeCountryFragmentState().observe(viewLifecycleOwner) {
            renderState(it)
        }
    }

    private fun renderState(state: CountryFragmentState) {

    }

}
