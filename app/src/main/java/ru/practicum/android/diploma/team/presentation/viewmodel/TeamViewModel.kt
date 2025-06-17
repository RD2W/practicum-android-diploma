package ru.practicum.android.diploma.team.presentation.viewmodel

import android.content.ActivityNotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.constants.AppConstants.CLICK_DEBOUNCE_DELAY_MILLIS
import ru.practicum.android.diploma.common.utils.debounce
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase
import ru.practicum.android.diploma.team.presentation.state.TeamScreenState
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ViewModel для экрана команды разработчиков
 * @property getDevTeamUseCase UseCase для получения списка разработчиков
 * @property openGithubProfileUseCase UseCase для открытия профиля GitHub
 */
class TeamViewModel(
    private val getDevTeamUseCase: GetDevTeamUseCase,
    private val openGithubProfileUseCase: OpenGithubProfileUseCase,
) : ViewModel() {
    // Приватный MutableStateFlow для хранения текущего состояния UI
    private val _state = MutableStateFlow<TeamScreenState>(TeamScreenState.Loading)

    /**
     * Публичный StateFlow для наблюдения за состоянием UI.
     * Предоставляет следующие состояния:
     * - Loading - загрузка данных
     * - Content - успешная загрузка с данными
     * - Error - ошибка загрузки
     */
    val state: StateFlow<TeamScreenState> = _state.asStateFlow()

    // Флаг для защиты от множественных кликов
    private val isClickAllowed = AtomicBoolean(true)
    private val clickDebounced = debounce<Unit>(
        delayMillis = CLICK_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = false,
    ) {
        isClickAllowed.set(true)
    }

    /**
     * Загружает список разработчиков команды
     */
    fun loadDevTeam() {
        _state.value = TeamScreenState.Loading
        viewModelScope.launch {
            try {
                val developers = getDevTeamUseCase()
                _state.value = TeamScreenState.Content(developers)
                Timber.d("Dev team loaded successfully: ${developers.size} developers")
            } catch (e: IOException) {
                _state.value = TeamScreenState.Error
                Timber.e(e, "Failed to load dev team: ${e.message}")
            }
        }
    }

    /**
     * Открывает профиль GitHub разработчика
     * @param githubLink Ссылка на профиль GitHub
     */
    fun openGithubProfile(githubLink: String) {
        if (isClickAllowed.compareAndSet(true, false)) {
            try {
                openGithubProfileUseCase(githubLink)
            } catch (e: ActivityNotFoundException) {
                // Нет приложения для обработки ссылки
                Timber.e(e, "No app to handle GitHub link: $githubLink")
            } catch (e: SecurityException) {
                // Нет разрешений
                Timber.e("Security exception for GitHub link: ${e.message}")
            } catch (e: IllegalArgumentException) {
                // Неправильный формат ссылки
                Timber.e(e, "Invalid GitHub link format: $githubLink")
            } finally {
                clickDebounced(Unit)
            }
        }
    }
}
