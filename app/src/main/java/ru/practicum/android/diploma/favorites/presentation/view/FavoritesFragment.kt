package ru.practicum.android.diploma.favorites.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.presentation.adapter.VacancyAdapter
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.presentation.state.FavoriteVacanciesScreenState
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoritesViewModel
import timber.log.Timber

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    // ViewBinding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = requireNotNull(_binding) { "Binding is null!" }

    // ViewModel
    private val viewModel: FavoritesViewModel by viewModel()

    // Адаптер для RecyclerView
    private val adapter = VacancyAdapter { vacancy ->
        // Логика, исполняемая по нажатию на элемент списка вакансий
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToVacancyFragment(vacancy.id)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)
        viewModel.loadFavorites()
        setupRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Настраивает RecyclerView с адаптером
     */
    private fun setupRecyclerView() {
        binding.rvFavoriteVacancies.adapter = adapter
    }

    /**
     * Подписывается на изменения состояния ViewModel
     */
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is FavoriteVacanciesScreenState.Loading -> showLoading()
                        is FavoriteVacanciesScreenState.Empty -> showEmptyPlaceholder()
                        is FavoriteVacanciesScreenState.Error -> showErrorPlaceholder()
                        is FavoriteVacanciesScreenState.Content -> showContent(state.vacancies)
                    }
                }
            }
        }
    }

    /**
     * Показывает список вакансий в избранном
     * @param vacancies Список вакансий для отображения
     */
    private fun showContent(vacancies: List<Vacancy>) {
        Timber.d("Vacancies: ${vacancies.joinToString { it.titleOfVacancy.toString() }}")
        switchUiMode(showVacancies = true)
        adapter.submitList(vacancies)
    }

    /**
     * Показывает индикатор загрузки
     */
    private fun showLoading() = switchUiMode(showProgressBar = true)

    /**
     * Показывает заглушку при отсутствии вакансий в избранном
     */
    private fun showEmptyPlaceholder() = switchUiMode(showEmptyPlaceholder = true)

    /**
     * Показывает заглушку при ошибке
     */
    private fun showErrorPlaceholder() = switchUiMode(showErrorPlaceholder = true)

    /**
     * Переключает видимость UI элементов
     * @param showVacancies Показывать ли список разработчиков
     * @param showEmptyPlaceholder Показывать ли заглушку при отсутствии избранного
     * @param showErrorPlaceholder Показывать ли заглушку при ошибке
     * @param showProgressBar Показывать ли индикатор загрузки
     */
    private fun switchUiMode(
        showVacancies: Boolean = false,
        showEmptyPlaceholder: Boolean = false,
        showErrorPlaceholder: Boolean = false,
        showProgressBar: Boolean = false,
    ) {
        with(binding) {
            rvFavoriteVacancies.isVisible = showVacancies
            groupPlaceholder.isVisible = showEmptyPlaceholder || showErrorPlaceholder
            progressIndicator.isVisible = showProgressBar
            progressIndicator.isIndeterminate = showProgressBar

            ivPlaceholder.setImageResource(
                if (showEmptyPlaceholder) {
                    R.drawable.image_placeholder_no_favorites
                } else {
                    R.drawable.image_placeholder_no_internet
                }
            )
            tvPlaceholder.text =
                resources.getString(
                    if (showEmptyPlaceholder) R.string.vacancy_empty_list else R.string.vacancy_not_found
                )
        }
    }
}
