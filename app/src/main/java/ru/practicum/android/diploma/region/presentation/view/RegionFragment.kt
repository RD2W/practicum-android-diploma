package ru.practicum.android.diploma.region.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.SharedFilterViewModel
import ru.practicum.android.diploma.region.domain.model.AreaRegion
import ru.practicum.android.diploma.region.presentation.adapter.AreasAdapter
import ru.practicum.android.diploma.region.presentation.state.AreaFragmentState
import ru.practicum.android.diploma.region.presentation.viewmodel.RegionViewModel
import kotlin.getValue

class RegionFragment : Fragment(R.layout.fragment_region) {

    private var _binding: FragmentRegionBinding? = null
    private val binding: FragmentRegionBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: RegionViewModel by viewModel()
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    private val adapter = AreasAdapter()
    private var areas = emptyList<AreaRegion>()
    private var countryId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegionBinding.bind(view)

        setupLiveDataObservers()
        setupRecyclerView()
        setupListeners()
        viewModel.getAreas(countryId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countryId = requireArguments().getString(AppConstants.COUNTRY_ID_KEY)
    }

    private fun setupLiveDataObservers() {
        viewModel.observeAreaFragmentState().observe(viewLifecycleOwner) {
            renderState(it)
        }
    }

    private fun setupRecyclerView() {
        binding.regionsRecycler.adapter = adapter
        binding.regionsRecycler.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupListeners() {
        adapter.onItemClickListener = { area ->
            sharedFilterViewModel.setArea(area)
            findNavController().navigateUp()
        }

        binding.regionEdit.doOnTextChanged { text, start, before, count ->
            filterAreasSet(text, areas)
            regionSearchButtonSetState(text)
        }
    }

    private fun renderState(state: AreaFragmentState) {
        when (state) {
            is AreaFragmentState.Content -> {
                showContent(state.areas)
                areas = state.areas
            }

            is AreaFragmentState.Error -> {
                showProblem()
            }

            is AreaFragmentState.Loading -> {
                showLoading()
            }
        }
    }

    private fun localRenderState(areas: List<AreaRegion>) {
        if (areas.isEmpty()) {
            showAbsent()
        } else {
            showContent(areas)
        }
    }

    private fun showContent(areas: List<AreaRegion>) {
        loadingViewsHide()
        problemViewsHide()
        absentViewsHide()
        contentViewsShow()
        adapter.areas.clear()
        adapter.areas.addAll(areas)
        adapter.notifyDataSetChanged()
    }

    private fun showProblem() {
        loadingViewsHide()
        contentViewsHide()
        problemViewsShow()
        absentViewsHide()
    }

    private fun showLoading() {
        problemViewsHide()
        contentViewsHide()
        loadingViewsShow()
        absentViewsHide()
    }

    private fun showAbsent() {
        loadingViewsHide()
        contentViewsHide()
        problemViewsHide()
        absentViewsShow()
    }

    private fun contentViewsShow() {
        binding.regionsRecycler.visibility = View.VISIBLE
    }

    private fun contentViewsHide() {
        binding.regionsRecycler.visibility = View.INVISIBLE
    }

    private fun problemViewsShow() {
        binding.regionProblemPlaceholderPic.visibility = View.VISIBLE
        binding.regionProblemPlaceholderText.visibility = View.VISIBLE
    }

    private fun problemViewsHide() {
        binding.regionProblemPlaceholderPic.visibility = View.INVISIBLE
        binding.regionProblemPlaceholderText.visibility = View.INVISIBLE
    }

    private fun loadingViewsShow() {
        binding.regionProgressbar.visibility = View.VISIBLE
    }

    private fun loadingViewsHide() {
        binding.regionProgressbar.visibility = View.INVISIBLE
    }

    private fun absentViewsShow() {
        binding.regionAbsentPlaceholderPic.visibility = View.VISIBLE
        binding.regionAbsentPlaceholderText.visibility = View.VISIBLE
    }

    private fun absentViewsHide() {
        binding.regionAbsentPlaceholderPic.visibility = View.INVISIBLE
        binding.regionAbsentPlaceholderText.visibility = View.INVISIBLE
    }

    private fun filterAreasSet(s: CharSequence?, areas: List<AreaRegion>) {
        val areasFiltered = when {
            !s.isNullOrEmpty() -> areas.filter { area -> area.name.contains(s, true) }
            else -> areas
        }
        localRenderState(areasFiltered)
    }

    private fun regionSearchButtonSetState(s: CharSequence?) {
        if (s.isNullOrEmpty()) {
            binding.regionClearButton.setImageResource(R.drawable.ic_search)
            binding.regionClearButton.isEnabled = false
        } else {
            binding.regionClearButton.setImageResource(R.drawable.ic_clear)
            binding.regionClearButton.isEnabled = true
        }
    }
}
