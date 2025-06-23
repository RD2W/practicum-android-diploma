package ru.practicum.android.diploma.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacanciesUseCase
import ru.practicum.android.diploma.favorites.presentation.state.FavoriteVacanciesScreenState
import timber.log.Timber

/**
 * ViewModel для экрана "Избранные вакансии".
 * Отвечает за:
 * - Загрузку списка избранных вакансий
 * - Управление состоянием UI
 * - Обработку ошибок
 *
 * @property getFavoritesUseCase UseCase для получения списка избранных вакансий
 */
class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoriteVacanciesUseCase,
) : ViewModel() {
    // Приватный MutableStateFlow для хранения текущего состояния UI
    private val _state = MutableStateFlow<FavoriteVacanciesScreenState>(
        FavoriteVacanciesScreenState.Loading
    )

    /**
     * Публичный StateFlow для наблюдения за состоянием UI.
     * Предоставляет следующие состояния:
     * - Loading - загрузка данных
     * - Content - успешная загрузка с данными
     * - Empty - список избранных пуст
     * - Error - ошибка загрузки
     */
    val state: StateFlow<FavoriteVacanciesScreenState> = _state.asStateFlow()

    /**
     * Загружает список избранных вакансий и обновляет состояние UI.
     * Логирует ключевые этапы загрузки.
     */
    fun loadFavorites() {
        Timber.d("Starting to load favorite vacancies")
        viewModelScope.launch {
            getFavoritesUseCase()
                .onStart {
                    Timber.d("Show loading state")
                    _state.value = FavoriteVacanciesScreenState.Loading
                }
                .catch { e ->
                    Timber.e(e, "Error loading favorite vacancies: ${e.message}")
                    _state.value = FavoriteVacanciesScreenState.Error
                }
                .collect { vacancies ->
                    when {
                        vacancies.isEmpty() -> {
                            Timber.d("Loaded empty favorites list")
                            _state.value = FavoriteVacanciesScreenState.Empty
                        }
                        else -> {
                            Timber.d("Successfully loaded %d favorite vacancies", vacancies.size)
                            _state.value = FavoriteVacanciesScreenState.Content(vacancies)
                        }
                    }
                }
        }
    }
}
