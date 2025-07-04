package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.SetFilterRepository

class SetFilterRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : SetFilterRepository {
    override fun setFilter(filter: Filter) {
        val json = gson.toJson(filter)
        sharedPreferences.edit {
            putString(AppConstants.FILTER_KEY, json)
        }
    }
}
