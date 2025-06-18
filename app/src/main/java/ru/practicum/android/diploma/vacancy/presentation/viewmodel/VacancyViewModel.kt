package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.favorites.domain.usecase.CheckIsFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacancyByIdUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.ToggleFavoriteStatusUseCase
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.usecase.GetVacancyDetailsByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.ShareVacancyUseCase
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyDetailsState
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyErrorType

/**
 * ViewModel для экрана деталей вакансии.
 * Отвечает за:
 * - Загрузку и хранение данных о вакансии
 * - Управление состоянием избранного
 * - Обработку ошибок
 * - Шаринг вакансии
 *
 * @param getVacancyDetailsByIdUseCase UseCase для получения деталей вакансии
 * @param shareVacancyUseCase UseCase для шаринга вакансии
 * @param toggleFavoriteStatusUseCase UseCase для переключения статуса избранного
 * @param checkIsFavoriteUseCase UseCase для проверки статуса избранного
 * @param getFavoriteVacancyByIdUseCase UseCase для получения вакансии из избранного
 */
class VacancyViewModel(
    private val getVacancyDetailsByIdUseCase: GetVacancyDetailsByIdUseCase,
    private val shareVacancyUseCase: ShareVacancyUseCase,
    private val toggleFavoriteStatusUseCase: ToggleFavoriteStatusUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    private val getFavoriteVacancyByIdUseCase: GetFavoriteVacancyByIdUseCase,
) : ViewModel() {

    /**
     * Текущее состояние экрана вакансии.
     * Доступно только для чтения через [state].
     */
    private val _state = MutableStateFlow<VacancyDetailsState>(VacancyDetailsState.Loading)

    /**
     * Публичный поток состояний экрана вакансии.
     * Используется UI слоем для отображения текущего состояния.
     */
    val state: StateFlow<VacancyDetailsState> = _state.asStateFlow()

    private var currentVacancy: VacancyDetails? = null
    private var currentVacancyId: String? = null

    /**
     * Загружает детали вакансии по указанному ID.
     * Автоматически проверяет статус избранного для данной вакансии.
     *
     * @param vacancyId Идентификатор вакансии для загрузки
     */
    fun loadVacancyDetails(vacancyId: String) {
        currentVacancyId = vacancyId
        viewModelScope.launch {
            _state.value = VacancyDetailsState.Loading
            val isFavorite = checkIsFavoriteUseCase(vacancyId).first()

            when (val result = getVacancyDetailsByIdUseCase(vacancyId)) {
                is Result.Success -> handleSuccess(result.data, isFavorite)
                is Result.ServerError -> handleError(isFavorite, vacancyId, VacancyErrorType.SERVER_ERROR)
                is Result.NotFound -> handleError(isFavorite, vacancyId, VacancyErrorType.NOT_FOUND)
                is Result.NoInternet -> handleError(isFavorite, vacancyId, VacancyErrorType.NO_INTERNET)
            }
        }
    }

    /**
     * Обрабатывает успешную загрузку данных вакансии.
     *
     * @param vacancy Загруженные данные вакансии
     * @param isFavorite Текущий статус избранного для вакансии
     */
    private fun handleSuccess(vacancy: VacancyDetails, isFavorite: Boolean) {
        currentVacancy = vacancy
        _state.value = VacancyDetailsState.Content(
            vacancy = vacancy,
            isFavorite = isFavorite
        )
    }

    /**
     * Обрабатывает ошибку загрузки данных вакансии.
     * Если вакансия есть в избранном, пытается загрузить её из локального хранилища.
     *
     * @param isFavorite Текущий статус избранного для вакансии
     * @param vacancyId Идентификатор вакансии
     * @param errorType Тип возникшей ошибки
     */
    private suspend fun handleError(
        isFavorite: Boolean,
        vacancyId: String,
        errorType: VacancyErrorType,
    ) {
        if (isFavorite) {
            getFavoriteVacancy(vacancyId, isFavorite)
        } else {
            _state.value = VacancyDetailsState.Error(
                errorType = errorType,
                isFavorite = isFavorite
            )
        }
    }

    /**
     * Пытается загрузить вакансию из избранного.
     *
     * @param vacancyId Идентификатор вакансии
     * @param isFavorite Текущий статус избранного
     */
    private suspend fun getFavoriteVacancy(vacancyId: String, isFavorite: Boolean) {
        getFavoriteVacancyByIdUseCase(vacancyId)?.let { favoriteVacancy ->
            currentVacancy = favoriteVacancy
            _state.value = VacancyDetailsState.Content(
                vacancy = favoriteVacancy,
                isFavorite = isFavorite
            )
        } ?: run {
            _state.value = VacancyDetailsState.Error(
                errorType = VacancyErrorType.NOT_FOUND,
                isFavorite = isFavorite
            )
        }
    }

    /**
     * Переключает статус избранного для текущей вакансии.
     * Обновляет UI состояние после изменения.
     */
    fun toggleFavoriteStatus() {
        viewModelScope.launch {
            currentVacancy?.let { vacancy ->
                toggleFavoriteStatusUseCase(vacancy).collect { isFavorite ->
                    _state.value = _state.value.let { currentState ->
                        when (currentState) {
                            is VacancyDetailsState.Content -> currentState.copy(isFavorite = isFavorite)
                            is VacancyDetailsState.Error -> currentState.copy(isFavorite = isFavorite)
                            else -> currentState
                        }
                    }
                }
            }
        }
    }

    /**
     * Запускает процесс шаринга текущей вакансии.
     * Использует системный диалог выбора приложения для шаринга.
     */
    fun shareVacancy() {
        currentVacancy?.let { vacancy ->
            if (vacancy.titleOfVacancy.isNotEmpty() && !vacancy.alternateUrl.isNullOrEmpty()) {
                shareVacancyUseCase(vacancy.titleOfVacancy, vacancy.alternateUrl)
            }
        }
    }

    /**
     * Повторяет попытку загрузки данных для текущей вакансии.
     */
    fun retryLoading() {
        currentVacancyId?.let { loadVacancyDetails(it) }
    }
}
