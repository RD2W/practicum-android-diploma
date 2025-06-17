package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.GetFilterRepository

class GetFilterRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : GetFilterRepository {
    override fun getFilter(): Filter? {
        val json = sharedPreferences.getString(AppConstants.FILTER_KEY, null) ?: return null
        return Gson().fromJson(json, Filter::class.java)
    }
}
