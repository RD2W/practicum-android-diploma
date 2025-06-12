package ru.practicum.android.diploma.search.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface HHApiService {

    @GET("/vacancies")
    // Возвращает список вакансий, размещенных в сервисе
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): HHApiResponse.Vacancies

    @GET("/vacancies/{id}")
    // Возвращает подробную информацию по указанной вакансии
    suspend fun getVacancyDetails(@Path("id") vacancyId: String): HHApiResponse.VacancyDetails

    @GET("/areas/countries")
    // Возвращает подмножество регионов, являющихся странами
    suspend fun getCountries(): HHApiResponse.Countries

    @GET("/areas/{id}")
    // Возвращает древовидный список регионов, начиная с указанного
    suspend fun getAreasByCountry(@Path("id") countryId: String): HHApiResponse.Areas

    @GET("/industries")
    // Возвращает двухуровневый справочник всех отраслей
    suspend fun getIndustries(): HHApiResponse.Industries

}
