package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.data.source.remote.HHApiRequest
import ru.practicum.android.diploma.search.data.source.remote.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.OK
import ru.practicum.android.diploma.search.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.search.domain.repository.VacanciesGetter

class VacanciesGetterImpl(private val networkClient: NetworkClient) : VacanciesGetter {
    override fun getVacancies(
        vacancyExpression: String,
        filter: Filter
    ): Flow<SearchVacanciesResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Vacancies(getOptions(filter)))

        if (response.responseCode == OK) {
            val vacancies = response.

            emit(SearchVacanciesResult.Success(v))
        } else {
            emit(HHApiResponse.BadResponse())
        }

    }

    private fun getOptions(filter: Filter): Map<String, String> {
        val options = mutableMapOf<String, String>()
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
