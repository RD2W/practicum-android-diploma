package ru.practicum.android.diploma.vacancy.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.utils.FormatStrings
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyDetailsState
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyErrorType
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyViewModel
import timber.log.Timber
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
        viewModel.loadVacancyDetails(vacancyId)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is VacancyDetailsState.Loading -> {
                            Timber.d("State: Loading | Показан индикатор загрузки")
                            showLoading()
                        }

                        is VacancyDetailsState.Content -> {
                            Timber.d(
                                "%snull",
                                "State: Content | Вакансия ID=${state.vacancy.id} " +
                                    "| isFavorite=${state.isFavorite} "
                            )
                            showVacancy(state.vacancy)
                            updateFavoriteIcon(state.isFavorite)
                        }

                        is VacancyDetailsState.Error -> {
                            val errorMessage = when (state.errorType) {
                                VacancyErrorType.SERVER_ERROR -> "Ошибка сервера (500)"
                                VacancyErrorType.NOT_FOUND -> "Вакансия не найдена (404)"
                                VacancyErrorType.NO_INTERNET -> "Нет интернета"
                            }
                            Timber.e(
                                "State: Error | Тип: $errorMessage " +
                                    "| isFavorite=${state.isFavorite}"
                            )
                            showError(state.errorType)
                            updateFavoriteIcon(state.isFavorite)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            scroll.visibility = View.GONE
            errorContainer.visibility = View.GONE
            progressIndicator.visibility = View.VISIBLE
            progressIndicator.isIndeterminate = true
        }
    }

    private fun showVacancy(vacancyDetails: VacancyDetails) {
        with(binding) {
            scroll.visibility = View.VISIBLE
            errorContainer.visibility = View.GONE
            progressIndicator.visibility = View.GONE

            vacancyTitle.text = vacancyDetails.titleOfVacancy
            vacancySalary.text = vacancyDetails.salary ?: getString(R.string.vacancy_salary_not_specified)
            vacancySphere.text = vacancyDetails.employerName
            vacancyCity.text = vacancyDetails.address ?: vacancyDetails.regionName
            vacancyExperienceMeaning.text = vacancyDetails.experience
            vacancySchedule.text = vacancyDetails.scheduleType
            vacancyDescription.text = vacancyDetails.description?.let { FormatStrings.htmlToFormattedText(it) }

            if (vacancyDetails.keySkills.isNullOrBlank()) {
                vacancyKeySkillsTitle.visibility = View.GONE
                vacancyKeySkills.visibility = View.GONE
            } else {
                val keySkillsInHtml = FormatStrings.keySkillsToHtmlList(vacancyDetails.keySkills)
                vacancyKeySkills.text = FormatStrings.htmlToFormattedText(keySkillsInHtml)
            }

            Glide.with(binding.root.context)
                .load(vacancyDetails.employerLogoUrl)
                .placeholder(R.drawable.ic_vacancy_placeholder)
                .fitCenter()
                .centerCrop()
                .into(binding.vacancyIcon)
        }

    }

    private fun showError(vacancyError: VacancyErrorType) {
        with(binding) {
            scroll.visibility = View.GONE
            errorContainer.visibility = View.VISIBLE
            progressIndicator.visibility = View.GONE

            textError.text = resources.getString(
                when (vacancyError) {
                    VacancyErrorType.SERVER_ERROR -> R.string.server_error
                    VacancyErrorType.NOT_FOUND -> R.string.vacancy_not_found_error
                    VacancyErrorType.NO_INTERNET -> R.string.no_internet
                }
            )

            errorImage.setImageResource(
                when (vacancyError) {
                    VacancyErrorType.SERVER_ERROR -> R.drawable.image_placeholder_server_error_vacancy
                    VacancyErrorType.NOT_FOUND -> R.drawable.image_placeholder_deleted_vacancy
                    VacancyErrorType.NO_INTERNET -> R.drawable.image_placeholder_no_internet
                }
            )

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
