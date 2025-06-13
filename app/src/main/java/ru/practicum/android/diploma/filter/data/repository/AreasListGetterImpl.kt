package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.domain.model.GetAreasListResult
import ru.practicum.android.diploma.filter.domain.repository.AreasListGetter
import ru.practicum.android.diploma.region.domain.model.Area
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient.Companion.OK

class AreasListGetterImpl(private val networkClient: NetworkClient) : AreasListGetter {
    override fun getAreasList(countryId: String): Flow<GetAreasListResult> = flow {
        val response = networkClient.doRequest(HHApiRequest.Areas(countryId))
        when (response.responseCode) {
            OK -> {
                val areaCountry = (response as HHApiResponse.Areas).areas.map {
                    Area(
                        id = it.id,
                        name = it.name,
                        parentId = null,
                        areas = it.areas?.map {
                            Area(
                                id = it.id,
                                name = it.name,
                                parentId = null,
                                areas = null
                            )
                        }
                    )
                }
                val areas = areaCountry[0].areas
                if (areas.isNullOrEmpty()) {
                    emit(GetAreasListResult.Success(areaCountry))
                } else {
                    emit(GetAreasListResult.Success(areas))
                }
            }

            else -> {
                emit(GetAreasListResult.Problem)
            }
        }
    }
}
