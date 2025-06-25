package ru.practicum.android.diploma.workplace.presentation.view

import android.content.Context
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
        get() = requireNotNull(_binding) { "Binding is null!" }

    private var country: Country? = null
    private var countryName: String? = null
    private var areaName: String? = null
    private var areaId: String? = null
    private var countryId: String? = null // new
    private var chosenCountryId: String? = null
    private var chosenCountryName: String? = null
    private var chosenAreaName: String? = null

    private val viewModel: WorkplaceViewModel by viewModel()
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkplaceBinding.bind(view)
        showContent(chosenCountryName, chosenAreaName)
        setupLiveDataObservers()
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        showContent(countryName, chosenAreaName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        chosenCountryId = requireArguments().getString(AppConstants.COUNTRY_ID_KEY)
        chosenCountryName = requireArguments().getString(AppConstants.COUNTRY_NAME_KEY, "")
        chosenAreaName = requireArguments().getString(AppConstants.AREA_NAME_KEY)
        if (chosenCountryId != null) {
            this.countryId = chosenCountryId // //new
        }
        if (chosenCountryName != "") {
            this.countryName = chosenCountryName // /new
        }

    }

    private fun setupLiveDataObservers() {
        sharedFilterViewModel.getCountry().observe(viewLifecycleOwner) { updatedCountry ->
            viewModel.synchronizeState(country = updatedCountry)
            this.country = updatedCountry
            this.countryName = updatedCountry?.name
            this.areaId = updatedCountry?.id
            this.countryId = updatedCountry?.id // new
        }

        sharedFilterViewModel.getArea().observe(viewLifecycleOwner) { updatedArea ->
            if (updatedArea?.countryId != null && updatedArea.countryName != null) {
                this.country = Country(updatedArea.countryId, updatedArea.countryName)
            }
            viewModel.synchronizeState(
                country = this.country,
                area = updatedArea,

            )
            this.areaName = updatedArea?.name
            this.areaId = updatedArea?.id
            if (updatedArea?.countryName != null) {
                this.countryId = updatedArea.countryId
                this.countryName = updatedArea.countryName
            }

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
            val bundle = Bundle() // этот поиск ищет нужные регионы
            val countryId = this.countryId ?: "" // country?.id ?: ""
            bundle.putString(AppConstants.COUNTRY_ID_KEY, countryId)
            findNavController().navigate(
                R.id.action_workplaceFragment_to_regionFragment,
                bundle
            )
        }

        binding.chooseWorkPlaceButton.setOnClickListener {
            prepareWorkplace()
            findNavController().navigateUp()
        }
    }

    private fun prepareWorkplace() {
        val workplace = Workplace(
            countryName = this.countryName,
            countryId = this.countryId,
            areaName = this.areaName,
            areaId = this.areaId
        )
        sharedFilterViewModel.setWorkplace(workplace)
    }

    private fun renderState(state: WorkplaceFragmentState) {
        when (state) {
            is WorkplaceFragmentState.Content -> {
                showContent(
                    state.country?.name ?: chosenCountryName,
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

}
