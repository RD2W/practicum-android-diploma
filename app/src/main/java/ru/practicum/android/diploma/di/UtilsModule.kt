package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.practicum.android.diploma.common.utils.NetworkUtils

val utilsModule = module {
    singleOf(::NetworkUtils)
}
