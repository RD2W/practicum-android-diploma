package ru.practicum.android.diploma.filter.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.presentation.state.FilterFragmentState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.SharedFilterViewModel
import kotlin.getValue

class FilterFragment : Fragment(R.layout.fragment_filter) {
    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }
    private var historyIndustryId: String? = null
    private var historyCountryId: String? = null
    private var historyCountryName: String? = null
    private var historyAreaName: String? = null
    private val viewModel: FilterViewModel by viewModel()
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterBinding.bind(view)
        setupLiveDataObservers()
        setupListeners()
        viewModel.synchronizeState()
    }
    override fun onStart() {
        super.onStart()
        salaryInnerHeadlineOutFocusSetTextColor(binding.desiredSalaryEdit.text.toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupLiveDataObservers() {
        viewModel.observeFilterFragmentState().observe(viewLifecycleOwner) { state ->
            renderState(state)
            when (state) {
                is FilterFragmentState.Content -> {
                    historyIndustryId = state.industryId
                    historyCountryId = state.countryId
                    historyCountryName = state.countryName
                    historyAreaName = state.areaName
                }
            }
        }
        viewModel.observeFilterApplyButtonState().observe(viewLifecycleOwner) { renderFilterApplyButtonState(it) }
        viewModel.observeFilterSkipButtonState().observe(viewLifecycleOwner) { renderFilterSkipButtonState(it) }
        sharedFilterViewModel.getWorkplace().observe(viewLifecycleOwner) { updatedWorkplace ->
            viewModel.synchronizeState(workplace = updatedWorkplace)
        }
        sharedFilterViewModel.getIndustry().observe(viewLifecycleOwner) { updatedIndustry ->
            viewModel.synchronizeState(industry = updatedIndustry)
            viewModel.checkUpdates()
        }
    }
    private fun setupListeners() {
        with(binding) {
            industryButton.setOnClickListener { industryButtonOnClick() }
            workPlaceButton.setOnClickListener { workplaceButtonOnClick() }
            desiredSalaryEdit.doOnTextChanged { text, start, before, count ->
                desiredSalaryEditOnChanged(text)
            }
            desiredSalaryEdit.doAfterTextChanged { viewModel.checkUpdates() }
            salaryClearButton.setOnClickListener { desiredSalaryEdit.setText("") }
            mustHaveSalaryChecker.setOnClickListener { mustHaveSalaryCheckerOnClick() }
            desiredSalaryEdit.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    salaryInnerHeadlineOutFocusSetTextColor(desiredSalaryEdit.text.toString())
                } else {
                    salaryInnerHeadlineInFocusSetTextColor()
                }
            }
            skipFilterButton.setOnClickListener { skipFilterButtonOnClick() }
            applyFilterButton.setOnClickListener { applyFilterButtonOnClick() }
            filter.setOnClickListener { desiredSalaryEdit.clearFocus() }
        }
    }
    private fun renderState(state: FilterFragmentState) {
        when (state) {
            is FilterFragmentState.Content -> {
                showContent(
                    state.workplaceName,
                    state.industryName,
                    state.salary?.from,
                    state.salaryMustHaveFlag,
                    state.skipButtonVisibility
                )
            }
            is FilterFragmentState.Loading -> {}
        }
    }
    private fun showContent(
        areaName: String?,
        industryName: String?,
        desiredSalary: Int?,
        salaryMustHaveFlag: Boolean?,
        skipButtonVisibility: Boolean
    ) {
        binding.workPlaceButton.text = areaName ?: resources.getText(R.string.button_work_place_name)
        workplaceButtonBannersSet(areaName)
        binding.industryButton.text = industryName ?: resources.getText(R.string.button_industry_name)
        industryButtonBannersSet(industryName)
        binding.desiredSalaryEdit.setText(desiredSalary?.toString())
        binding.mustHaveSalaryChecker.isChecked = salaryMustHaveFlag == true
        viewModel.checkFilterLoad()
        salaryInnerHeadlineOutFocusSetTextColor(desiredSalary?.toString())
    }
    private fun workplaceButtonBannersSet(value: Any?) {
        if (value == null) {
            binding.workplaceInner.visibility = View.INVISIBLE
            binding.workPlaceButton.setTextColor(resources.getColor(R.color.button_item_empty_text_color))
        } else {
            binding.workplaceInner.visibility = View.VISIBLE
            binding.workPlaceButton.setTextColor(resources.getColor(R.color.button_item_text_color))
        }
    }
    private fun industryButtonBannersSet(value: Any?) {
        if (value == null) {
            binding.industryInner.visibility = View.INVISIBLE
            binding.industryButton.setTextColor(resources.getColor(R.color.button_item_empty_text_color))
        } else {
            binding.industryInner.visibility = View.VISIBLE
            binding.industryButton.setTextColor(resources.getColor(R.color.button_item_text_color))
        }
    }
    private fun applyButtonVisibilitySet(isVisible: Boolean) {
        binding.applyFilterButton.isVisible = isVisible
    }
    private fun salaryClearButtonVisibilitySet(isVisible: Boolean) {
        binding.salaryClearButton.isVisible = isVisible
    }
    private fun skipButtonVisibilitySet(isVisible: Boolean) {
        binding.skipFilterButton.isVisible = isVisible
    }
    private fun salaryInnerHeadlineOutFocusSetTextColor(s: String?) {
        if (s.isNullOrEmpty()) {
            binding.salaryInner.setTextColor(resources.getColor(R.color.edit_text_hint_color))
        } else {
            binding.salaryInner.setTextColor(resources.getColor(R.color.salary_not_null_text_color))
        }
    }
    private fun salaryInnerHeadlineInFocusSetTextColor() {
        binding.salaryInner.setTextColor(resources.getColor(R.color.blue))
    }
    private fun renderFilterApplyButtonState(isVisible: Boolean) {
        applyButtonVisibilitySet(isVisible)
    }
    private fun renderFilterSkipButtonState(isVisible: Boolean) {
        skipButtonVisibilitySet(isVisible)
    }
    private fun mustHaveSalaryCheckerOnClick() {
        binding.desiredSalaryEdit.clearFocus()
        viewModel.updateSalaryMustHaveFlagValue(binding.mustHaveSalaryChecker.isChecked)
        viewModel.checkUpdates()
    }
    private fun desiredSalaryEditOnChanged(text: CharSequence?) {
        salaryClearButtonVisibilitySet(!text.isNullOrEmpty())
        viewModel.updateSalaryValue(text?.toString()?.toIntOrNull())
    }
    private fun skipFilterButtonOnClick() {
        viewModel.skipValuesAndFilter()
        sharedFilterViewModel.resetValues()
        binding.desiredSalaryEdit.clearFocus()
    }
    private fun applyFilterButtonOnClick() {
        viewModel.applyFilter()
        binding.desiredSalaryEdit.clearFocus()
        findNavController().navigateUp()
    }
    private fun industryButtonOnClick() {
        val bundle = Bundle()
        bundle.putString(AppConstants.INDUSTRY_ID_KEY, this.historyIndustryId ?: "")
        findNavController().navigate(R.id.action_filterFragment_to_industryFragment, bundle)
    }
    private fun workplaceButtonOnClick() {
        val bundle = Bundle()
        bundle.putString(AppConstants.COUNTRY_ID_KEY, this.historyCountryId ?: "")
        bundle.putString(AppConstants.COUNTRY_NAME_KEY, this.historyCountryName ?: "")
        bundle.putString(AppConstants.AREA_NAME_KEY, this.historyAreaName ?: "")
        findNavController().navigate(R.id.action_filterFragment_to_workplaceFragment, bundle)
    }
}
