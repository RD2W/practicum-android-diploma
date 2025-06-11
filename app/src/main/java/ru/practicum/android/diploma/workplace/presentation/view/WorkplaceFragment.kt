package ru.practicum.android.diploma.workplace.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.workplace.presentation.viewmodel.WorkplaceViewModel
import kotlin.getValue

class WorkplaceFragment : Fragment(R.layout.fragment_workplace) {

    private var _binding: FragmentWorkplaceBinding? = null
    private val binding: FragmentWorkplaceBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: WorkplaceViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkplaceBinding.bind(view)
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtonListeners() {
        binding.countryButton.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToCountryFragment()
            )
        }

        binding.regionButton.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToRegionFragment()
            )
        }
    }
}
