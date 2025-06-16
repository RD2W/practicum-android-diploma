package ru.practicum.android.diploma.search.data.source.remote

import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.common.utils.NetworkUtils
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection

class RetrofitClient(private val hhApiService: HHApiService, private val networkUtils: NetworkUtils) : NetworkClient {
    override suspend fun doRequest(request: Any): HHApiResponse {
        preValidation(request)?.let { return it }

        return withContext(Dispatchers.IO) {
            try {
                Timber.d("[API] Search request: $request")
                val response = executeRequest(request as HHApiRequest)
                Timber.d("[API] Get response: $response")
                response.apply { responseCode = HttpURLConnection.HTTP_OK }
            } catch (e: HttpException) {
                Timber.e("[API] HTTP error occurred: ${e.code()}")
                handleHttpExceptions(e)
            } catch (e: JsonParseException) {
                Timber.e("[API] Json parsing problems: ${e.message}")
                HHApiResponse.BadResponse("Json parse error: ${e.message}").apply { responseCode = HttpURLConnection.HTTP_BAD_REQUEST }
            } catch (e: IOException) {
                Timber.e("[API] Network problems: ${e.message}")
                HHApiResponse.BadResponse("Network error: ${e.message}").apply { responseCode = NETWORK_ERROR }
            }
        }
    }

    private fun preValidation(request: Any): HHApiResponse? {
        return if (request !is HHApiRequest) {
            Timber.e("[API] Invalid request type: $request")
            HHApiResponse.BadResponse("Invalid request type")
                .apply { responseCode = HttpURLConnection.HTTP_BAD_REQUEST }
        } else if (!networkUtils.isNetworkAvailable()) {
            Timber.e("[API] No Internet connection")
            return HHApiResponse.BadResponse("No Internet connection").apply { responseCode = NETWORK_ERROR }
        } else {
            null
        }
    }

    private suspend fun executeRequest(request: HHApiRequest): HHApiResponse {
        return when (request) {
            is HHApiRequest.Vacancies -> getVacancies(request.options)
            is HHApiRequest.VacancyDetails -> getVacancyDetails(request.id)
            is HHApiRequest.Countries -> getCountries()
            is HHApiRequest.Areas -> getAreasByCountry(request.id)
            is HHApiRequest.Industries -> getIndustries()
        }
    }

    private fun handleHttpExceptions(e: HttpException): HHApiResponse {
        return when (e.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> HHApiResponse.BadResponse("Unauthorized")
                .apply { responseCode = HttpURLConnection.HTTP_UNAUTHORIZED }

            HttpURLConnection.HTTP_FORBIDDEN -> HHApiResponse.BadResponse("Forbidden")
                .apply { responseCode = HttpURLConnection.HTTP_FORBIDDEN }

            HttpURLConnection.HTTP_NOT_FOUND -> HHApiResponse.BadResponse("Nothing found")
                .apply { responseCode = HttpURLConnection.HTTP_NOT_FOUND }

            else -> HHApiResponse.BadResponse("HTTP error: ${e.message}")
                .apply { responseCode = HttpURLConnection.HTTP_INTERNAL_ERROR }
        }
    }

    private suspend fun getVacancies(options: Map<String, String>): HHApiResponse.Vacancies {
        return hhApiService.searchVacancies(options)
    }

    private suspend fun getVacancyDetails(id: String): HHApiResponse.VacancyDetails {
        return hhApiService.getVacancyDetails(id)
    }

    private suspend fun getCountries(): HHApiResponse.Countries {
        val response = hhApiService.getCountries()
        return HHApiResponse.Countries(countries = response)
    }

    private suspend fun getAreasByCountry(id: String): HHApiResponse.Areas {
        val response = hhApiService.getAreasByCountry(id)
        return HHApiResponse.Areas(areas = response)
    }

    private suspend fun getIndustries(): HHApiResponse.Industries {
        val response = hhApiService.getIndustries()
        return HHApiResponse.Industries(industries = response)
    }

    companion object {
        const val NETWORK_ERROR = -1
    }

}
