package ru.practicum.android.diploma.filter.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.common.constants.AppConstants
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.repository.CheckFilterLoadRepository

class CheckFilterLoadRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CheckFilterLoadRepository {
    override fun checkFilterLoad(): Boolean {
        val json = sharedPreferences.getString(AppConstants.FILTER_KEY, null) ?: return false
        val filter = gson.fromJson(json, Filter::class.java)
        return filterLoadCheck(filter)
    }

    private fun filterLoadCheck(filter: Filter): Boolean {
        return !(filter.workplace?.areaId == null
            &&
            filter.industry?.id == null
            &&
            filter.salary == null
            &&
            filter.onlyWithSalary == null)
    }
}
