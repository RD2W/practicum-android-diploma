package ru.practicum.android.diploma.vacancy.data.repository

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import com.google.gson.JsonParseException
import ru.practicum.android.diploma.common.domain.model.Result
import ru.practicum.android.diploma.common.utils.NetworkUtils
import ru.practicum.android.diploma.search.data.mapper.toVacancyDetails
import ru.practicum.android.diploma.search.data.model.HHApiRequest
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.source.remote.RetrofitClient
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Реализация репозитория для работы с деталями вакансии.
 *
 * @param context Контекст приложения (для шаринга)
 * @param retrofitClient Клиент для работы с API HeadHunter
 * @param networkUtils Утилита для проверки состояния сети
 */
class VacancyDetailsRepositoryImpl(
    private val context: Context,
    private val retrofitClient: RetrofitClient,
    private val networkUtils: NetworkUtils,
) : VacancyDetailsRepository {
    /**
     * Получение деталей вакансии по ID.
     *
     * @param vacancyId Идентификатор вакансии
     * @return Результат операции (Result<VacancyDetails>)
     */
    override suspend fun getVacancyDetails(vacancyId: String): Result<VacancyDetails> {
        return if (networkUtils.isNetworkAvailable()) {
            try {
                val response = retrofitClient.doRequest(HHApiRequest.VacancyDetails(vacancyId))
                handleApiResponse(response)
            } catch (e: IOException) {
                handleNetworkException(e)
            } catch (e: JsonParseException) { // Для ошибок парсинга JSON
                Result.ServerError(e)
            } catch (e: IllegalStateException) { // Для ошибок состояния
                Result.ServerError(e)
            }
        } else {
            Result.NoInternet
        }
    }

    /**
     * Шаринг ссылки на вакансию через Intent.
     *
     * @param titleOfVacancy Название вакансии (будет заголовком диалога шаринга)
     * @param alternateUrl Ссылка на вакансию
     */
    override fun shareVacancyLink(titleOfVacancy: String, alternateUrl: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, alternateUrl)
        }
        val chooserIntent = Intent.createChooser(shareIntent, titleOfVacancy).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivitySafely(chooserIntent)
    }

    private fun startActivitySafely(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Timber.e(e, "No activity found to handle intent")
            }
        } else {
            Timber.e("No activity found to handle intent: ${intent.action}")
        }
    }

    private fun handleApiResponse(response: HHApiResponse): Result<VacancyDetails> {
        return when (response) {
            is HHApiResponse.VacancyDetails -> handleSuccessResponse(response)
            is HHApiResponse.BadResponse -> handleErrorResponse(response)
            else -> handleUnexpectedResponse()
        }
    }

    private fun handleSuccessResponse(response: HHApiResponse.VacancyDetails): Result<VacancyDetails> {
        return response.toVacancyDetails().let { domainModel ->
            Result.Success(domainModel)
        }
    }

    private fun handleErrorResponse(response: HHApiResponse.BadResponse): Result<VacancyDetails> {
        return when (response.responseCode) {
            HttpURLConnection.HTTP_NOT_FOUND -> Result.NotFound
            RetrofitClient.NETWORK_ERROR -> Result.NoInternet
            else -> createServerError(response.errorMessage)
        }
    }

    private fun handleUnexpectedResponse(): Result<VacancyDetails> {
        return Result.ServerError(IllegalStateException("Unexpected response type"))
    }

    private fun handleNetworkException(e: IOException): Result<VacancyDetails> {
        Timber.e(e, "Network error occurred")
        return Result.NoInternet
    }

    private fun createServerError(errorMessage: String?): Result<VacancyDetails> {
        val message = errorMessage?.takeIf { it.isNotBlank() } ?: "Server error"
        return Result.ServerError(IllegalStateException(message))
    }
}
