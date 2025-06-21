package ru.practicum.android.diploma.country.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.country.domain.model.Country
import ru.practicum.android.diploma.country.presentation.adapter.CountriesAdapter
import ru.practicum.android.diploma.country.presentation.state.CountryFragmentState
import ru.practicum.android.diploma.country.presentation.viewmodel.CountryViewModel
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.SharedFilterViewModel
import kotlin.getValue

class CountryFragment : Fragment(R.layout.fragment_country) {

    private var _binding: FragmentCountryBinding? = null
    private val binding: FragmentCountryBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: CountryViewModel by viewModel()
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    private val adapter = CountriesAdapter()
    private var countries = emptyList<Country>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCountryBinding.bind(view)

        setupLiveDataObservers()
        setupRecyclerView()
        setupListeners()
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

    private fun setupRecyclerView() {
        binding.countriesRecycler.adapter = adapter
        binding.countriesRecycler.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupListeners() {
        adapter.onItemClickListener = { country ->
            sharedFilterViewModel.setCountry(country)
            findNavController().navigateUp()
        }
    }

    private fun renderState(state: CountryFragmentState) {
        when (state) {
            is CountryFragmentState.Content -> {
                showContent(state.countries)
                countries = state.countries
            }

            is CountryFragmentState.Problem -> {
                showProblem()
            }

            is CountryFragmentState.Loading -> {
                showLoading()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(countries: List<Country>) {
        loadingViewsHide()
        problemViewsHide()
        contentViewsShow()
        adapter.countries.clear()
        adapter.countries.addAll(countries)
        adapter.notifyDataSetChanged()
    }

    private fun showProblem() {
        loadingViewsHide()
        contentViewsHide()
        problemViewsShow()
    }

    private fun showLoading() {
        problemViewsHide()
        contentViewsHide()
        loadingViewsShow()
    }

    private fun contentViewsShow() {
        binding.countriesRecycler.visibility = View.VISIBLE
    }

    private fun contentViewsHide() {
        binding.countriesRecycler.visibility = View.INVISIBLE
    }

    private fun problemViewsShow() {
        binding.countryProblemPlaceholderPic.visibility = View.VISIBLE
        binding.countryProblemPlaceholderText.visibility = View.VISIBLE
    }

    private fun problemViewsHide() {
        binding.countryProblemPlaceholderPic.visibility = View.INVISIBLE
        binding.countryProblemPlaceholderText.visibility = View.INVISIBLE
    }

    private fun loadingViewsShow() {
        binding.countryProgressbar.visibility = View.VISIBLE
    }

    private fun loadingViewsHide() {
        binding.countryProgressbar.visibility = View.INVISIBLE
    }

}
