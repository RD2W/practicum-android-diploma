package ru.practicum.android.diploma.favorites.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import ru.practicum.android.diploma.favorites.domain.usecase.ToggleFavoriteStatusUseCase
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

/**
 * Use case для управления состоянием избранного (добавление/удаление)
 * @property addToFavoriteUseCase Use case для добавления в избранное
 * @property removeFromFavoriteUseCase Use case для удаления из избранного
 * @property checkIsFavoriteUseCase Use case для проверки статуса избранного
 */
class ToggleFavoriteStatusUseCaseImpl(
    private val addToFavoriteUseCase: AddToFavoriteUseCaseImpl,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCaseImpl,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCaseImpl,
) : ToggleFavoriteStatusUseCase {
    /**
     * Переключает состояние избранного для вакансии
     * @param vacancyDetails Вакансия для изменения статуса
     * @return Flow<Boolean> Новый статус избранного (true - после добавления, false - после удаления)
     */
    override suspend operator fun invoke(vacancyDetails: VacancyDetails): Flow<Boolean> {
        if (checkIsFavoriteUseCase(vacancyDetails.id).first()) {
            removeFromFavoriteUseCase(vacancyDetails.id)
            return checkIsFavoriteUseCase(vacancyDetails.id)
        } else {
            addToFavoriteUseCase(vacancyDetails)
            return checkIsFavoriteUseCase(vacancyDetails.id)
        }
    }
}
