package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.usecase.AddToFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.CheckIsFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacanciesUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacancyByIdUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.RemoveFromFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.ToggleFavoriteStatusUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetAreasListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.search.domain.usecase.GetVacanciesUseCase
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase

val domainModule = module {
    /** Developers Team UseCases */
    factoryOf(::GetDevTeamUseCase)
    factoryOf(::OpenGithubProfileUseCase)
    /** Favorite Vacancies UseCases */
    factoryOf(::AddToFavoriteUseCase)
    factoryOf(::CheckIsFavoriteUseCase)
    factoryOf(::GetFavoriteVacanciesUseCase)
    factoryOf(::GetFavoriteVacancyByIdUseCase)
    factoryOf(::RemoveFromFavoriteUseCase)
    factoryOf(::ToggleFavoriteStatusUseCase)
    /** Any UseCases */
    factoryOf(::GetVacanciesUseCase)
    factoryOf(::GetCountriesListUseCase)
    factoryOf(::GetAreasListUseCase)
    factoryOf(::GetIndustriesListUseCase)
    /** Search Vacancies UseCases */
    factoryOf(::SearchUseCase)
}
