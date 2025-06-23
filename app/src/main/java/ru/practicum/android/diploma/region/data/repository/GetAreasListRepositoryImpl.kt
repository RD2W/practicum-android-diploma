package ru.practicum.android.diploma.region.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.mapper.toAreaRegion
import ru.practicum.android.diploma.region.domain.model.AreaRegion
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
        if (countryId.isEmpty()) {
            val response = networkClient.doRequest(HHApiRequest.Countries)
            when (response.responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    val countries = (response as HHApiResponse.Countries).countries
                    var areas = emptyList<AreaRegion>()
                    countries.forEach { area1 ->
                        val countryId = area1.id
                        val countryName = area1.name
                        area1.areas?.forEach { area2 ->
                            areas = areas + AreaRegion(area2.id, area2.name, countryId, countryName)
                        }
                    }

                    emit(GetAreasListResult.Success(areas.sortedBy { areas -> areas.name }))
                }
                else -> {
                    emit(GetAreasListResult.Error)
                }
            }
        } else {
            val response = networkClient.doRequest(HHApiRequest.Areas(countryId))
            when (response.responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    val areaRegions = (response as HHApiResponse.Areas).chosenArea.areas!!
                        .map { areaDto -> areaDto.toAreaRegion() }
                    emit(GetAreasListResult.Success(areaRegions))
                }
                else -> {
                    emit(GetAreasListResult.Error)
                }
            }
        }
    }
}
