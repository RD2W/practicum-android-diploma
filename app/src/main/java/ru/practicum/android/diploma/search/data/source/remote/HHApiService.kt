package ru.practicum.android.diploma.search.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.model.HHApiResponse
import ru.practicum.android.diploma.search.data.model.dto.AreaDto
import ru.practicum.android.diploma.search.data.model.dto.IndustryDto

/**
 * Документация к API [по ссылке](https://api.hh.ru/openapi/redoc)
 */
interface HHApiService {

    /**
     * Раздел документации [по ссылке](https://api.hh.ru/openapi/redoc#tag/Poisk-vakansij/operation/get-vacancies)
     */
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): HHApiResponse.Vacancies

    @GET("/vacancies/{id}")
    /**
     * Раздел документации [по ссылке](https://api.hh.ru/openapi/redoc#tag/Vakansii/operation/get-vacancy)
     */
    suspend fun getVacancyDetails(@Path("id") vacancyId: String): HHApiResponse.VacancyDetails

    @GET("/areas")
    /**
     * Раздел документации [по ссылке](https://api.hh.ru/openapi/redoc#tag/Obshie-spravochniki/operation/get-areas)
     */
    suspend fun getCountries(): List<AreaDto>

    @GET("/areas/{id}")
    /**
     * Раздел документации [по ссылке](https://api.hh.ru/openapi/redoc#tag/Obshie-spravochniki/operation/get-areas-from-specified)
     */
    suspend fun getAreasByCountry(@Path("id") countryId: String): AreaDto

    @GET("/industries")
    /**
     * Раздел документации [по ссылке](https://api.hh.ru/openapi/redoc#tag/Obshie-spravochniki/operation/get-industries)
     */
    suspend fun getIndustries(): List<IndustryDto>

}
