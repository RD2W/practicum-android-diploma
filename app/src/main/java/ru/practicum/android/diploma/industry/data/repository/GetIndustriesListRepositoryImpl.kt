package ru.practicum.android.diploma.industry.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.industry.domain.model.GetIndustriesListResult
import ru.practicum.android.diploma.industry.domain.model.Industry
import ru.practicum.android.diploma.industry.domain.repository.GetIndustriesListRepository
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import java.net.HttpURLConnection

class GetIndustriesListRepositoryImpl(
    private val networkClient: NetworkClient
) : GetIndustriesListRepository {
    override fun getIndustriesList(): Flow<GetIndustriesListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Industries)
        when (response.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                val industries = (response as HHApiResponse.Industries).industries.map {
                    Industry(
                        id = it.id,
                        name = it.name
                    )
                }
                emit(GetIndustriesListResult.Success(industries))
            }
            else -> {
                emit(GetIndustriesListResult.Error)
            }
        }
    }
}
