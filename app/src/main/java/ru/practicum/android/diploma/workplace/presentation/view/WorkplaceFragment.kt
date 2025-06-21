package ru.practicum.android.diploma.workplace.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.SharedFilterViewModel
import ru.practicum.android.diploma.workplace.domain.model.Workplace
import ru.practicum.android.diploma.workplace.presentation.state.WorkplaceFragmentState
import ru.practicum.android.diploma.workplace.presentation.viewmodel.WorkplaceViewModel
import kotlin.getValue

class WorkplaceFragment : Fragment(R.layout.fragment_workplace) {

    private var _binding: FragmentWorkplaceBinding? = null
    private val binding: FragmentWorkplaceBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private var country: Country? = null
    private var countryName: String? = null
    private var areaName: String? = null
    private var areaId: String? = null

    private val viewModel: WorkplaceViewModel by viewModel()
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkplaceBinding.bind(view)

        setupLiveDataObservers()
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLiveDataObservers() {
        sharedFilterViewModel.getCountry().observe(viewLifecycleOwner) { updatedCountry ->
            viewModel.synchronizeState(country = updatedCountry)
            this.country = updatedCountry
            this.countryName = updatedCountry?.name
            this.areaId = updatedCountry?.id
        }

        sharedFilterViewModel.getArea().observe(viewLifecycleOwner) { updatedArea ->
            viewModel.synchronizeState(
                country = this.country,
                area = updatedArea
            )
            this.areaName = updatedArea?.name
            this.areaId = updatedArea?.id
        }


        viewModel.observeWorkplaceFragmentState().observe(viewLifecycleOwner) {
            renderState(it)
        }
    }

    private fun setupButtonListeners() {
        binding.countryButton.setOnClickListener {
            findNavController().navigate(
                WorkplaceFragmentDirections.actionWorkplaceFragmentToCountryFragment(),
            )
        }

        binding.regionButton.setOnClickListener {
            if (this.country != null) {
                val bundle = Bundle()
                bundle.putString(AppConstants.COUNTRY_ID_KEY, country!!.id.toString())
                findNavController().navigate(
                    R.id.action_workplaceFragment_to_regionFragment, bundle
                )
            }
        }

        binding.chooseWorkPlaceButton.setOnClickListener {
            prepareWorkplace()
            findNavController().navigateUp()
        }
    }

    private fun prepareWorkplace() {
        val workplace = Workplace(
            countryName = this.countryName,
            areaName = this.areaName,
            areaId = this.areaId
        )
        sharedFilterViewModel.setWorkplace(workplace)
    }

    private fun renderState(state: WorkplaceFragmentState) {
        when (state) {
            is WorkplaceFragmentState.Content -> {
                showContent(
                    state.country?.name,
                    state.area?.name
                )
            }

            is WorkplaceFragmentState.Loading -> {}
        }
    }

    private fun showContent(
        countryName: String?,
        areaName: String?,
    ) {
        binding.countryButton.text = countryName ?: resources.getText(R.string.button_country_name)
        countryButtonBannersSet(countryName)
        binding.regionButton.text = areaName ?: resources.getText(R.string.button_region_name)
        areaButtonBannersSet(areaName)

        chooseWorkplaceButtonVisibilitySet(countryName, areaName)
    }

    private fun countryButtonBannersSet(value: Any?) {
        if (value == null) {
            binding.countryInner.visibility = View.INVISIBLE
            binding.countryButton.setTextColor(resources.getColor(R.color.button_item_empty_text_color))
        } else {
            binding.countryInner.visibility = View.VISIBLE
            binding.countryButton.setTextColor(resources.getColor(R.color.button_item_text_color))
        }
    }

    private fun areaButtonBannersSet(value: Any?) {
        if (value == null) {
            binding.areaInner.visibility = View.INVISIBLE
            binding.regionButton.setTextColor(resources.getColor(R.color.button_item_empty_text_color))
        } else {
            binding.areaInner.visibility = View.VISIBLE
            binding.regionButton.setTextColor(resources.getColor(R.color.button_item_text_color))
        }
    }

    private fun chooseWorkplaceButtonVisibilitySet(countryName: String?, areaName: String?) {
        chooseWorkplaceButtonIsVisible(!countryName.isNullOrEmpty() || !areaName.isNullOrEmpty())
    }

    private fun chooseWorkplaceButtonIsVisible(isVisible: Boolean) {
        if (isVisible) {
            binding.chooseWorkPlaceButton.visibility = View.VISIBLE
        } else {
            binding.chooseWorkPlaceButton.visibility = View.INVISIBLE
        }
    }

    private fun regionItemButtonAbilitySet(isEnabled: Boolean) {
        binding.regionButton.isEnabled = isEnabled
    }
}
