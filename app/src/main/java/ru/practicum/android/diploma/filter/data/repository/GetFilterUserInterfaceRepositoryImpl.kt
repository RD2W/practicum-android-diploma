package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.GetFilterUserInterfaceRepository

class GetFilterUserInterfaceRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : GetFilterUserInterfaceRepository {
    override fun getFilter(): Filter? {
        val json = sharedPreferences.getString(AppConstants.FILTER_UI_KEY, null) ?: return null
        return gson.fromJson(json, Filter::class.java)
    }
}
