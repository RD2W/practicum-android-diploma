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
import ru.practicum.android.diploma.favorites.domain.usecase.impl.AddToFavoriteUseCaseImpl
import ru.practicum.android.diploma.favorites.domain.usecase.impl.CheckIsFavoriteUseCaseImpl
import ru.practicum.android.diploma.favorites.domain.usecase.impl.GetFavoriteVacanciesUseCaseImpl
import ru.practicum.android.diploma.favorites.domain.usecase.impl.GetFavoriteVacancyByIdUseCaseImpl
import ru.practicum.android.diploma.favorites.domain.usecase.impl.RemoveFromFavoriteUseCaseImpl
import ru.practicum.android.diploma.favorites.domain.usecase.impl.ToggleFavoriteStatusUseCaseImpl
import ru.practicum.android.diploma.region.domain.usecase.GetAreasListUseCase
import ru.practicum.android.diploma.country.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.industry.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.region.domain.usecase.impl.GetAreasListUseCaseImpl
import ru.practicum.android.diploma.country.domain.usecase.impl.GetCountriesListUseCaseImpl
import ru.practicum.android.diploma.filter.domain.usecase.GetFilterUseCase
import ru.practicum.android.diploma.filter.domain.usecase.SetFilterUseCase
import ru.practicum.android.diploma.filter.domain.usecase.impl.GetFilterUseCaseImpl
import ru.practicum.android.diploma.filter.domain.usecase.impl.SetFilterUseCaseImpl
import ru.practicum.android.diploma.industry.domain.usecase.impl.GetIndustriesListUseCaseImpl
import ru.practicum.android.diploma.search.domain.usecase.SearchUseCase
import ru.practicum.android.diploma.search.domain.usecase.impl.SearchUseCaseImpl
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase
import ru.practicum.android.diploma.team.domain.usecase.impl.GetDevTeamUseCaseImpl
import ru.practicum.android.diploma.team.domain.usecase.impl.OpenGithubProfileUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.usecase.GetVacancyDetailsByIdUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.ShareVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.impl.GetVacancyDetailsByIdUseCaseImpl
import ru.practicum.android.diploma.vacancy.domain.usecase.impl.ShareVacancyUseCaseImpl

val domainModule = module {
    /** Developers Team UseCases */
    factoryOf(::GetDevTeamUseCaseImpl) { bind<GetDevTeamUseCase>() }
    factoryOf(::OpenGithubProfileUseCaseImpl) { bind<OpenGithubProfileUseCase>() }
    /** Favorite Vacancies UseCases */
    factoryOf(::AddToFavoriteUseCaseImpl) { bind<AddToFavoriteUseCase>() }
    factoryOf(::CheckIsFavoriteUseCaseImpl) { bind<CheckIsFavoriteUseCase>() }
    factoryOf(::GetFavoriteVacanciesUseCaseImpl) { bind<GetFavoriteVacanciesUseCase>() }
    factoryOf(::GetFavoriteVacancyByIdUseCaseImpl) { bind<GetFavoriteVacancyByIdUseCase>() }
    factoryOf(::RemoveFromFavoriteUseCaseImpl) { bind<RemoveFromFavoriteUseCase>() }
    factoryOf(::ToggleFavoriteStatusUseCaseImpl) { bind<ToggleFavoriteStatusUseCase>() }
    /** Search Vacancies UseCases */
    factoryOf(::SearchUseCaseImpl) { bind<SearchUseCase>() }
    /** Get Vacancy Details UseCases */
    factoryOf(::GetVacancyDetailsByIdUseCaseImpl) { bind<GetVacancyDetailsByIdUseCase>() }
    factoryOf(::ShareVacancyUseCaseImpl) { bind<ShareVacancyUseCase>() }
    /** Filter UseCases */
    factoryOf(::GetAreasListUseCaseImpl) { bind<GetAreasListUseCase>() }
    factoryOf(::GetCountriesListUseCaseImpl) { bind<GetCountriesListUseCase>() }
    factoryOf(::GetIndustriesListUseCaseImpl) { bind<GetIndustriesListUseCase>() }
    factoryOf(::GetFilterUseCaseImpl) { bind<GetFilterUseCase>() }
    factoryOf(::SetFilterUseCaseImpl) { bind<SetFilterUseCase>() }
}
