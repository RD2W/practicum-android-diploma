package ru.practicum.android.diploma.vacancy.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyDetailsState
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyViewModel
import kotlin.getValue

class VacancyFragment : Fragment(R.layout.fragment_vacancy) {

    private var _binding: FragmentVacancyBinding? = null
    private val binding: FragmentVacancyBinding
        get() = requireNotNull(_binding) { "Binding wasn't initialized!" }

    private val viewModel: VacancyViewModel by viewModel()
    private val args: VacancyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVacancyBinding.bind(view)
        loadVacancyDetails(args.vacancyId)
        observeViewModel()
        setupButtonListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadVacancyDetails(vacancyId: String) {
        viewModel.loadVacancyDetails("121510619")
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is VacancyDetailsState.Loading -> { }
                        is VacancyDetailsState.Content -> {
                            updateFavoriteIcon(state.isFavorite)
                        }
                        is VacancyDetailsState.Error -> {
                            updateFavoriteIcon(state.isFavorite)
                        }
                    }
                }
            }
        }
    }

    private fun setupButtonListeners() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            topAppBar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.icShare -> {
                        viewModel.shareVacancy()
                        true
                    }
                    R.id.icFavorite -> {
                        viewModel.toggleFavoriteStatus()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.topAppBar.menu.findItem(R.id.icFavorite)?.let { menuItem ->
            val iconRes = if (isFavorite) R.drawable.ic_favorite_active else R.drawable.ic_favorite_inactive
            menuItem.setIcon(iconRes)
        }
    }
}
