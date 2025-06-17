package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.SetFilterRepository

class SetFilterRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SetFilterRepository {
    override fun setFilter(filter: Filter) {
        val json = Gson().toJson(filter)
        sharedPreferences.edit()
            .putString(AppConstants.FILTER_KEY, json)
            .apply()
    }
}
