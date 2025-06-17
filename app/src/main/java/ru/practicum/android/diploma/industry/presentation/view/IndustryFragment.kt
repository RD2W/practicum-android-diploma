package ru.practicum.android.diploma.industry.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.SharedFilterViewModel
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.industry.presentation.adapter.IndustriesAdapter
import ru.practicum.android.diploma.industry.presentation.state.IndustryFragmentState
import ru.practicum.android.diploma.industry.presentation.viewmodel.IndustryViewModel
import kotlin.getValue

class IndustryFragment : Fragment(R.layout.fragment_industry) {

    private var _binding: FragmentIndustryBinding? = null
    private val binding: FragmentIndustryBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: IndustryViewModel by viewModel()
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    private val adapter = IndustriesAdapter()
    private var industry: Industry? = null
    private var industries = emptyList<Industry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIndustryBinding.bind(view)

        setupLiveDataObservers()
        setupRecyclerView()
        setupListeners()
        viewModel.getIndustries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLiveDataObservers() {
        viewModel.observeIndustryFragmentState().observe(viewLifecycleOwner) {
            renderState(it)
        }
    }

    private fun setupRecyclerView() {
        binding.industriesRecycler.adapter = adapter
        binding.industriesRecycler.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupListeners() {
        adapter.onItemClickListener = { industry ->
            applyButtonShow()
            this.industry = industry
        }

        binding.chooseIndustryButton.setOnClickListener {
            if (industry != null) {
                sharedFilterViewModel.setIndustry(industry!!)
            }
            findNavController().navigateUp()
        }

        binding.industryEdit.addTextChangedListener(textWatcherCustom())

        binding.industryClearButton.setOnClickListener {
            binding.industryEdit.setText("")
        }

    }

    private fun renderState(state: IndustryFragmentState) {
        when (state) {
            is IndustryFragmentState.Content -> {
                showContent(state.industries)
                industries = state.industries
            }

            is IndustryFragmentState.Problem -> {
                showProblem()
            }

            is IndustryFragmentState.Loading -> {
                showLoading()
            }
        }
    }

    private fun localRenderState(industries: List<Industry>) {
        if (industries.isEmpty()) {
            showAbsent()
        } else {
            showContent(industries)
        }
    }

    private fun showContent(industries: List<Industry>) {
        loadingViewsHide()
        problemViewsHide()
        absentViewsHide()
        contentViewsShow()
        adapter.industries.clear()
        adapter.industries.addAll(industries)
        adapter.notifyDataSetChanged()
    }

    private fun showProblem() {
        loadingViewsHide()
        contentViewsHide()
        absentViewsHide()
        problemViewsShow()
    }

    private fun showLoading() {
        problemViewsHide()
        absentViewsHide()
        contentViewsHide()
        loadingViewsShow()
    }

    private fun showAbsent() {
        loadingViewsHide()
        contentViewsHide()
        problemViewsHide()
        absentViewsShow()
        applyButtonHide()
    }

    private fun contentViewsShow() {
        binding.industriesRecycler.visibility = View.VISIBLE
    }

    private fun contentViewsHide() {
        binding.industriesRecycler.visibility = View.INVISIBLE
    }

    private fun problemViewsShow() {
        binding.industryProblemPlaceholderPic.visibility = View.VISIBLE
        binding.industryProblemPlaceholderText.visibility = View.VISIBLE
    }

    private fun problemViewsHide() {
        binding.industryProblemPlaceholderPic.visibility = View.INVISIBLE
        binding.industryProblemPlaceholderText.visibility = View.INVISIBLE
    }

    private fun loadingViewsShow() {
        binding.industryProgressbar.visibility = View.VISIBLE
    }

    private fun loadingViewsHide() {
        binding.industryProgressbar.visibility = View.INVISIBLE
    }

    private fun absentViewsShow() {
        binding.industryAbsentPlaceholderPic.visibility = View.VISIBLE
        binding.industryAbsentPlaceholderText.visibility = View.VISIBLE
    }

    private fun absentViewsHide() {
        binding.industryAbsentPlaceholderPic.visibility = View.INVISIBLE
        binding.industryAbsentPlaceholderText.visibility = View.INVISIBLE
    }

    private fun applyButtonShow() {
        binding.chooseIndustryButton.visibility = View.VISIBLE
    }

    private fun applyButtonHide() {
        binding.chooseIndustryButton.visibility = View.GONE
    }

    private fun textWatcherCustom(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            industrySearchButtonSetState(s)
            filterIndustriesSet(s, industries)
            adapter.selectedPosition = -1
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun industrySearchButtonSetState(s: CharSequence?) {
        if (s.isNullOrEmpty()) {
            binding.industryClearButton.setImageResource(R.drawable.ic_search)
            binding.industryClearButton.isEnabled = false
        } else {
            binding.industryClearButton.setImageResource(R.drawable.ic_clear)
            binding.industryClearButton.isEnabled = true
        }
    }

    private fun filterIndustriesSet(s: CharSequence?, industries: List<Industry>) {
        val industriesFiltered = when {
            !s.isNullOrEmpty() -> industries.filter { industry -> industry.name.contains(s, true) }
            else -> industries
        }
        localRenderState(industriesFiltered)
    }

}
