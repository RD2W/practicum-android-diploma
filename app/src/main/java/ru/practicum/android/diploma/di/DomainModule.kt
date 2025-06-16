package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.usecase.AddToFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.CheckIsFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacanciesUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.GetFavoriteVacancyByIdUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.RemoveFromFavoriteUseCase
import ru.practicum.android.diploma.favorites.domain.usecase.ToggleFavoriteStatusUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetAreasListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetAreasUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.usecase.impl.GetAreasUseCaseImpl
import ru.practicum.android.diploma.filter.domain.usecase.impl.GetCountriesUseCaseImpl
import ru.practicum.android.diploma.filter.domain.usecase.impl.GetIndustriesUseCaseImpl
import ru.practicum.android.diploma.search.domain.usecase.GetVacanciesUseCase
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.GetVacancyDetailsByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.ShareVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.impl.GetVacancyDetailsByIdUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.usecase.impl.ShareVacancyUseCaseImpl

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
    /** Get Vacancy Details UseCases */
    factoryOf(::GetVacancyDetailsByIdUseCaseImpl) { bind<GetVacancyDetailsByIdUseCase>() }
    factoryOf(::ShareVacancyUseCaseImpl) { bind<ShareVacancyUseCase>() }
    /** Filter UseCases */
    factoryOf(::GetAreasUseCaseImpl) { bind<GetAreasUseCase>() }
    factoryOf(::GetCountriesUseCaseImpl) { bind<GetCountriesUseCase>() }
    factoryOf(::GetIndustriesUseCaseImpl) { bind<GetIndustriesUseCase>() }
}
