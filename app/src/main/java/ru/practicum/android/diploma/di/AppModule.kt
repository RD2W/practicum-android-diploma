package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.country.presentation.viewmodel.CountryViewModel
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.industry.presentation.viewmodel.IndustryViewModel
import ru.practicum.android.diploma.region.presentation.viewmodel.RegionViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.team.presentation.viewmodel.TeamViewModel
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyViewModel
import ru.practicum.android.diploma.workplace.presentation.viewmodel.WorkplaceViewModel

val appModule = module {
    viewModelOf(::CountryViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::IndustryViewModel)
    viewModelOf(::RegionViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::TeamViewModel)
    viewModelOf(::VacancyViewModel)
    viewModelOf(::WorkplaceViewModel)
}
