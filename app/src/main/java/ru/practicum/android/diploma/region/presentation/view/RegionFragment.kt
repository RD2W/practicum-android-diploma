package ru.practicum.android.diploma.region.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.region.presentation.viewmodel.RegionViewModel
import kotlin.getValue

class RegionFragment : Fragment(R.layout.fragment_region) {

    private var _binding: FragmentRegionBinding? = null
    private val binding: FragmentRegionBinding
        get() = requireNotNull(_binding) { "Binding is null!" }

    private val viewModel: RegionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegionBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
