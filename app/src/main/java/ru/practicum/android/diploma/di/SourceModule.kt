package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.team.data.source.DataSource
import ru.practicum.android.diploma.team.data.source.local.LocalDataSource

val sourceModule = module {
    factoryOf(::LocalDataSource) { bind<DataSource>() }
}
