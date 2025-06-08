package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.team.presentation.viewmodel.TeamViewModel

val appModule = module {
    viewModelOf(::TeamViewModel)
}
