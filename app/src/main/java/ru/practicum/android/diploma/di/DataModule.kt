package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.team.data.repository.DevTeamRepositoryImpl
import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository

val dataModule = module {
    factoryOf(::DevTeamRepositoryImpl) { bind<DevTeamRepository>() }
}
