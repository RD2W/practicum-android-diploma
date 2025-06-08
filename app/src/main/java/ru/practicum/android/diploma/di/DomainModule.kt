package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.team.domain.usecase.GetDevTeamUseCase
import ru.practicum.android.diploma.team.domain.usecase.OpenGithubProfileUseCase

val domainModule = module {
    /** Developers Team UseCases */
    factoryOf(::GetDevTeamUseCase)
    factoryOf(::OpenGithubProfileUseCase)
}
