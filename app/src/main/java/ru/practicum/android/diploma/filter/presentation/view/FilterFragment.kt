package ru.practicum.android.diploma.filter.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.filter.presentation.state.FilterFragmentState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.SharedFilterViewModel
import kotlin.getValue

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

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
        viewModel.observeFilterFragmentState().observe(viewLifecycleOwner) {
            renderState(it)
        }

        viewModel.observeFilterApplyButtonState().observe(viewLifecycleOwner) {
            renderFilterApplyButtonState(it)
        }

        viewModel.observeFilterSkipButtonState().observe(viewLifecycleOwner) {
            renderFilterSkipButtonState(it)
        }

        sharedFilterViewModel.getWorkplace().observe(viewLifecycleOwner) { updatedWorkplace ->
            viewModel.synchronizeState(workplace = updatedWorkplace)
        }

        sharedFilterViewModel.getIndustry().observe(viewLifecycleOwner) { updatedIndustry ->
            viewModel.synchronizeState(industry = updatedIndustry)
            viewModel.checkUpdates()
        }
    }

    private fun setupListeners() {
        binding.industryButton.setOnClickListener {
            findNavController().navigate(
                FilterFragmentDirections.actionFilterFragmentToIndustryFragment()
            )
        }

        binding.workPlaceButton.setOnClickListener {
            findNavController().navigate(
                FilterFragmentDirections.actionFilterFragmentToWorkplaceFragment()
            )
        }

        binding.desiredSalaryEdit.addTextChangedListener(textWatcherCustom())

        binding.salaryClearButton.setOnClickListener {
            binding.desiredSalaryEdit.setText("")
        }

        binding.unknownSalaryChecker.setOnClickListener {
            binding.desiredSalaryEdit.clearFocus()
            viewModel.checkFilterLoad()
            viewModel.updateSalaryMustHaveFlagValue(binding.unknownSalaryChecker.isChecked)
            viewModel.checkUpdates()
        }

        binding.desiredSalaryEdit.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                salaryInnerHeadlineOutFocusSetTextColor(binding.desiredSalaryEdit.text.toString())
            } else {
                salaryInnerHeadlineInFocusSetTextColor()
            }
        }

        binding.skipFilterButton.setOnClickListener {
            viewModel.skipValuesAndFilter()
            sharedFilterViewModel.resetValues()
            binding.desiredSalaryEdit.clearFocus()
        }

        binding.applyFilterButton.setOnClickListener {
            viewModel.applyFilter()
            sharedFilterViewModel.resetValues()
            binding.desiredSalaryEdit.clearFocus()
            findNavController().navigateUp()
        }

        binding.filter.setOnClickListener {
            binding.desiredSalaryEdit.clearFocus()
        }
    }

    private fun renderState(state: FilterFragmentState) {
        when (state) {
            is FilterFragmentState.Content -> {
                showContent(
                    state.workplaceName,
                    state.industryName,
                    state.salary?.from,
                    state.salaryMustHaveFlag!!,
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
        salaryMustHaveFlag: Boolean,
        skipButtonVisibility: Boolean
    ) {
        binding.workPlaceButton.text = areaName ?: resources.getText(R.string.button_work_place_name)
        workplaceButtonBannersSet(areaName)
        binding.industryButton.text = industryName ?: resources.getText(R.string.button_industry_name)
        industryButtonBannersSet(industryName)
        binding.desiredSalaryEdit.setText(desiredSalary?.toString())
        binding.unknownSalaryChecker.isChecked = salaryMustHaveFlag
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

    private fun textWatcherCustom(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            salaryClearButtonVisibilitySet(!s.isNullOrEmpty())
            viewModel.checkFilterLoad()
            viewModel.updateSalaryValue(s?.toString()?.toIntOrNull())
        }

        override fun afterTextChanged(s: Editable?) {
            viewModel.checkUpdates()
        }
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

}
