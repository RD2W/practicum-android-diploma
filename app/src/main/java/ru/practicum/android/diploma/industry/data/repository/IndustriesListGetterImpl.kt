package ru.practicum.android.diploma.industry.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.industry.domain.model.GetIndustriesListResult
import ru.practicum.android.diploma.industry.domain.repository.IndustriesListGetter
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.search.data.source.remote.HHApiRequest
import ru.practicum.android.diploma.search.data.source.remote.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.OK

class IndustriesListGetterImpl(private val networkClient: NetworkClient) : IndustriesListGetter {
    override fun getIndustriesList(): Flow<GetIndustriesListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Industries)
        when (response.responseCode) {
            OK -> {
                val industries = (response as HHApiResponse.Industries).industries.map {
                    Industry(
                        id = it.id,
                        name = it.name
                    )
                }
                emit(GetIndustriesListResult.Success(industries))
            }
            else -> {
                emit(GetIndustriesListResult.Problem)
            }
        }
    }
}
