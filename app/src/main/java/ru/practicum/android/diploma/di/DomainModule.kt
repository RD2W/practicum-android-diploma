package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.domain.usecase.GetAreasListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesListUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetIndustriesListUseCase
import ru.practicum.android.diploma.search.domain.usecase.GetVacanciesUseCase
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase

val domainModule = module {
    /** Developers Team UseCases */
    factoryOf(::GetDevTeamUseCase)
    factoryOf(::OpenGithubProfileUseCase)
    factoryOf(::GetVacanciesUseCase)
    factoryOf(::GetCountriesListUseCase)
    factoryOf(::GetAreasListUseCase)
    factoryOf(::GetIndustriesListUseCase)
}
