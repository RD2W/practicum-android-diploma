package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.practicum.android.diploma.country.data.repository.GetCountriesListRepositoryImpl
import ru.practicum.android.diploma.country.domain.repository.GetCountriesListRepository
import ru.practicum.android.diploma.favorites.data.repository.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteVacanciesRepository
import ru.practicum.android.diploma.filter.data.repository.CheckFilterLoadRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.GetFilterRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.GetFilterUserInterfaceRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.SetFilterRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.SetFilterUserInterfaceRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.CheckFilterLoadRepository
import ru.practicum.android.diploma.filter.domain.repository.GetFilterRepository
import ru.practicum.android.diploma.filter.domain.repository.GetFilterUserInterfaceRepository
import ru.practicum.android.diploma.filter.domain.repository.SetFilterRepository
import ru.practicum.android.diploma.filter.domain.repository.SetFilterUserInterfaceRepository
import ru.practicum.android.diploma.industry.data.repository.GetIndustriesListRepositoryImpl
import ru.practicum.android.diploma.industry.domain.repository.GetIndustriesListRepository
import ru.practicum.android.diploma.region.data.repository.GetAreasListRepositoryImpl
import ru.practicum.android.diploma.region.domain.repository.GetAreasListRepository
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
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
    factoryOf(::GetCountriesListRepositoryImpl) { bind<GetCountriesListRepository>() }
    factoryOf(::GetAreasListRepositoryImpl) { bind<GetAreasListRepository>() }
    factoryOf(::GetIndustriesListRepositoryImpl) { bind<GetIndustriesListRepository>() }
    /** Search Vacancies Repository */
    factoryOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    /** Vacancy Repository */
    factoryOf(::VacancyDetailsRepositoryImpl) { bind<VacancyDetailsRepository>() }
    /** Filter Repository */
    factoryOf(::GetFilterRepositoryImpl) { bind<GetFilterRepository>() }
    factoryOf(::SetFilterRepositoryImpl) { bind<SetFilterRepository>() }
    factoryOf(::CheckFilterLoadRepositoryImpl) { bind<CheckFilterLoadRepository>() }
    factoryOf(::GetFilterUserInterfaceRepositoryImpl) { bind<GetFilterUserInterfaceRepository>() }
    factoryOf(::SetFilterUserInterfaceRepositoryImpl) { bind<SetFilterUserInterfaceRepository>() }
}
