package ru.practicum.android.diploma.search.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.search.data.mapper.toMap
import ru.practicum.android.diploma.search.data.mapper.toSearchResult
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.NetworkClient
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient
import ru.practicum.android.diploma.search.domain.model.SearchParameters
import ru.practicum.android.diploma.search.domain.model.SearchResult
import ru.practicum.android.diploma.search.domain.repository.SearchRepository
import timber.log.Timber
import java.net.HttpURLConnection

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences,
) : SearchRepository {
    override suspend fun searchVacancies(searchParameters: SearchParameters): Flow<Result<SearchResult>> = flow {
        val request = HHApiRequest.Vacancies(searchParameters.toMap())
        when (val response = networkClient.doRequest(request)) {
            is HHApiResponse.Vacancies -> {
                emit(Result.Success(response.toSearchResult()))
            }

            is HHApiResponse.BadResponse -> {
                val result = when (response.responseCode) {
                    HttpURLConnection.HTTP_NOT_FOUND -> Result.NotFound
                    RetrofitClient.NETWORK_ERROR -> Result.NoInternet
                    else -> Result.ServerError(
                        Exception(response.errorMessage.takeIf { it.isNotBlank() } ?: "Server error")
                    )
                }
                emit(result)
            }

            else -> {
                emit(Result.ServerError(IllegalStateException("Unexpected error")))
            }
        }
    }

    override fun getFilter(): Flow<Filter?> = callbackFlow {
        // Первоначальное значение с обработкой ошибок
        val initialJson = sharedPrefs.getString(AppConstants.FILTER_KEY, null)
        val initialFilter = parseFilterSafely(initialJson)
        trySend(initialFilter)

        // Слушатель изменений с проверкой на реальные изменения
        var lastFilter: Filter? = initialFilter
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == AppConstants.FILTER_KEY) {
                val newJson = sharedPrefs.getString(key, null)
                val newFilter = parseFilterSafely(newJson)

                // Отправляем только если фильтр действительно изменился
                if (newFilter != lastFilter) {
                    lastFilter = newFilter
                    trySend(newFilter).onFailure { e ->
                        Timber.e(e, "Failed to send filter update")
                    }
                }
            }
        }

        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    private fun parseFilterSafely(json: String?): Filter? {
        return try {
            json?.let {
                gson.fromJson(it, Filter::class.java).takeIf { filter ->
                    // Дополнительная проверка на валидность фильтра
                    filter.workplace != null || filter.industry != null
                        || filter.salary != null || filter.onlyWithSalary != null
                }
            }
        } catch (e: JsonParseException) {
            Timber.e(e, "Failed to parse filter from JSON")
            null
        }
    }
}
