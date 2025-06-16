package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.repository.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.filter.data.repository.AreasListGetterImpl
import ru.practicum.android.diploma.filter.data.repository.CountriesListGetterImpl
import ru.practicum.android.diploma.filter.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.IndustriesListGetterImpl
import ru.practicum.android.diploma.filter.domain.repository.AreasListGetter
import ru.practicum.android.diploma.filter.domain.repository.CountriesListGetter
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository
import ru.practicum.android.diploma.filter.domain.repository.IndustriesListGetter
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.data.repository.VacanciesGetterImpl
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.search.domain.repository.VacanciesGetter
import ru.practicum.android.diploma.team.data.repository.DevTeamRepositoryImpl
import ru.practicum.android.diploma.team.domain.repository.DevTeamRepository
import ru.practicum.android.diploma.vacancy.data.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

val dataModule = module {
    /** Developers Team Repository */
    factoryOf(::DevTeamRepositoryImpl) { bind<DevTeamRepository>() }
    /** Favorite Vacancies Repository */
    factoryOf(::FavoriteVacanciesRepositoryImpl) { bind<FavoriteVacanciesRepository>() }
    /** Any Repository */
    factoryOf(::VacanciesGetterImpl) { bind<VacanciesGetter>() }
    factoryOf(::CountriesListGetterImpl) { bind<CountriesListGetter>() }
    factoryOf(::AreasListGetterImpl) { bind<AreasListGetter>() }
    factoryOf(::IndustriesListGetterImpl) { bind<IndustriesListGetter>() }
    /** Search Vacancies Repository */
    factoryOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    /** Vacancy Repository */
    factoryOf(::VacancyDetailsRepositoryImpl) { bind<VacancyDetailsRepository>() }
    /** Filter Repository */
    factoryOf(::FilterRepositoryImpl) { bind<FilterRepository>() }
}
