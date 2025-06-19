package ru.practicum.android.diploma.region.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.mapper.toAreaRegion
import ru.practicum.android.diploma.region.domain.model.GetAreasListResult
import ru.practicum.android.diploma.region.domain.repository.GetAreasListRepository
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import java.net.HttpURLConnection

class GetAreasListRepositoryImpl(
    private val networkClient: NetworkClient
) : GetAreasListRepository {
    override fun getAreasList(countryId: String): Flow<GetAreasListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Areas(countryId))
        when (response.responseCode) {
            HttpURLConnection.HTTP_OK -> {
                val areaRegions = (response as HHApiResponse.Areas).chosenArea.areas!!
                    .map { areaDto -> areaDto.toAreaRegion() }
                emit(GetAreasListResult.Success(areaRegions))
            }

            else -> {
                emit(GetAreasListResult.Problem)
            }
        }
    }
}
