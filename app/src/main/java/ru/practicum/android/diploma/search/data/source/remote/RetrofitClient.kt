package ru.practicum.android.diploma.search.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.common.utils.NetworkUtils
import timber.log.Timber
import java.io.IOException

class RetrofitClient(private val hhApiService: HHApiService, private val networkUtils: NetworkUtils) : NetworkClient {
    override suspend fun doRequest(request: Any): HHApiResponse {
        preValidation(request)?.let { return it }

        return withContext(Dispatchers.IO) {
            try {
                Timber.d("[API] Search request: $request")
                val response = executeRequest(request as HHApiRequest)
                Timber.d("[API] Get response: $response")
                response.apply { responseCode = OK }
            } catch (e: HttpException) {
                Timber.e("[API] HTTP error occurred: ${e.code()}")
                handleHttpExceptions(e)
            } catch (e: IOException) {
                Timber.e("[API] Network problems: ${e.message}")
                HHApiResponse.BadResponse("Network error: ${e.message}").apply { responseCode = NETWORK_ERROR }
            }
        }
    }

    private fun preValidation(request: Any): HHApiResponse? {
        if (request !is HHApiRequest) {
            Timber.e("[API] Invalid request type: $request")
            HHApiResponse.BadResponse("Invalid request type").apply { responseCode = BAD_REQUEST }
        }
        if (!networkUtils.isNetworkAvailable()) {
            Timber.e("[API] No Internet connection")
            HHApiResponse.BadResponse("No Internet connection").apply { responseCode = NETWORK_ERROR }
        }
        return null
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
            UNAUTHORIZED -> HHApiResponse.BadResponse("Unauthorized").apply { responseCode = UNAUTHORIZED }
            FORBIDDEN -> HHApiResponse.BadResponse("Forbidden").apply { responseCode = FORBIDDEN }
            NOT_FOUND -> HHApiResponse.BadResponse("Nothing found").apply { responseCode = NOT_FOUND }
            else -> HHApiResponse.BadResponse("HTTP error: ${e.message}").apply { responseCode = SERVER_ERROR }
        }
    }

    private suspend fun getVacancies(options: Map<String, String>): HHApiResponse {
        return hhApiService.searchVacancies(options)
    }

    private suspend fun getVacancyDetails(id: String): HHApiResponse {
        return hhApiService.getVacancyDetails(id)
    }

    private suspend fun getCountries(): HHApiResponse {
        return hhApiService.getCountries()
    }

    private suspend fun getAreasByCountry(id: String): HHApiResponse {
        return hhApiService.getAreasByCountry(id)
    }

    private suspend fun getIndustries(): HHApiResponse {
        return hhApiService.getIndustries()
    }

    companion object {
        const val OK = 200
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403 // captcha
        const val NOT_FOUND = 404
        const val SERVER_ERROR = 500
        const val NETWORK_ERROR = -1
    }

}
