package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.data.source.remote.HHApiRequest
import ru.practicum.android.diploma.search.data.source.remote.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.BAD_REQUEST
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.FORBIDDEN
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.NETWORK_ERROR
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.NOT_FOUND
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.OK
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.SERVER_ERROR
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.UNAUTHORIZED
import ru.practicum.android.diploma.search.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.search.domain.repository.VacanciesGetter

class VacanciesGetterImpl(private val networkClient: NetworkClient) : VacanciesGetter {
    override fun getVacancies(
        vacancyExpression: String,
        filter: Filter
    ): Flow<SearchVacanciesResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Vacancies(getOptions(vacancyExpression, filter)))

        when (response.responseCode) {
            OK -> {
                val vacancies = (response as HHApiResponse.Vacancies).items.map {
                    Vacancy(
                        id = it.id,
                        titleOfVacancy = it.name,
                        regionName = it.area.name,
                        salary = it.salary.from.toString(),
                        employerName = it.employer.name,
                        employerLogoUrl = it.employer.logoUrls?.size90,
                    )
                }
                if (vacancies.isEmpty()) {
                    emit(SearchVacanciesResult.NoVacancies)
                } else {
                    emit(SearchVacanciesResult.Success(vacancies))
                }
            }
            NETWORK_ERROR -> {
                emit(SearchVacanciesResult.NetworkError((response as HHApiResponse.BadResponse).errorMessage))
            }
            BAD_REQUEST -> {
                emit(SearchVacanciesResult.BadRequest((response as HHApiResponse.BadResponse).errorMessage))
            }
            UNAUTHORIZED -> {
                emit(SearchVacanciesResult.Unauthorized((response as HHApiResponse.BadResponse).errorMessage))
            }
            FORBIDDEN -> {
                emit(SearchVacanciesResult.Forbidden((response as HHApiResponse.BadResponse).errorMessage))
            }
            NOT_FOUND -> {
                emit(SearchVacanciesResult.NoVacancies)
            }
            SERVER_ERROR -> {
                emit(SearchVacanciesResult.ServerError((response as HHApiResponse.BadResponse).errorMessage))
            }
        }
    }

    private fun getOptions(
        text: String,
        filter: Filter
    ): Map<String, String> {
        val options = mutableMapOf<String, String>()
        if (text != "") {
            options.put("text", text)
        }
        if (filter.areaId != null) {
            options.put("area", filter.areaId)
        }
        if (filter.industryId != null) {
            options.put("industry", filter.industryId)
        }
        if (filter.salary != null) {
            options.put("only_with_salary", true)
            options.put("currency", filter.salary.currency)
            options.put("salary", filter.salary.from)
        } else {
            options.put("only_with_salary", false)
        }

        return options
    }
}
