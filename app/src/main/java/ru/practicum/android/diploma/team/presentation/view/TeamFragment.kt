package ru.practicum.android.diploma.team.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentTeamBinding
import ru.practicum.android.diploma.team.domain.model.Developer
import ru.practicum.android.diploma.team.presentation.adapter.DeveloperAdapter
import ru.practicum.android.diploma.team.presentation.state.TeamScreenState
import ru.practicum.android.diploma.team.presentation.viewmodel.TeamViewModel
import timber.log.Timber

/**
 * Фрагмент для отображения команды разработчиков
 */
class TeamFragment : Fragment(R.layout.fragment_team) {

    // ViewBinding
    private var _binding: FragmentTeamBinding? = null
    private val binding: FragmentTeamBinding
        get() = requireNotNull(_binding) { "Binding is null!" }

    // ViewModel
    private val viewModel: TeamViewModel by viewModel()

    // Адаптер для RecyclerView
    private val adapter = DeveloperAdapter { developer ->
        developer.githubProfile?.let { viewModel.openGithubProfile(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTeamBinding.bind(view)
        viewModel.loadDevTeam()
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
        binding.rvDevelopers.adapter = adapter
    }

    /**
     * Подписывается на изменения состояния ViewModel
     */
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is TeamScreenState.Loading -> showLoading()
                        is TeamScreenState.Content -> showContent(state.developers)
                        is TeamScreenState.Error -> showPlaceholder()
                    }
                }
            }
        }
    }

    /**
     * Показывает список разработчиков
     * @param developers Список разработчиков для отображения
     */
    private fun showContent(developers: List<Developer>) {
        Timber.d("Developers: ${developers.joinToString { it.fullName.toString() }}")
        switchUiMode(showDevelopers = true)
        adapter.submitList(developers)
    }

    /**
     * Показывает индикатор загрузки
     */
    private fun showLoading() = switchUiMode(showProgressBar = true)

    /**
     * Показывает заглушку при ошибке
     */
    private fun showPlaceholder() = switchUiMode(showErrorPlaceholder = true)

    /**
     * Переключает видимость UI элементов
     * @param showDevelopers Показывать ли список разработчиков
     * @param showErrorPlaceholder Показывать ли заглушку
     * @param showProgressBar Показывать ли индикатор загрузки
     */
    private fun switchUiMode(
        showDevelopers: Boolean = false,
        showErrorPlaceholder: Boolean = false,
        showProgressBar: Boolean = false,
    ) {
        with(binding) {
            groupDevelopers.isVisible = showDevelopers
            ivErrorPlaceholder.isVisible = showErrorPlaceholder
            progressIndicator.isVisible = showProgressBar
            progressIndicator.isIndeterminate = showProgressBar
        }
    }
}
