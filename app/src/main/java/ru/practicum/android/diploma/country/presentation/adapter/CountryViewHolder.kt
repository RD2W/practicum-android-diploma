package ru.practicum.android.diploma.country.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.country.domain.model.Country

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val countryTextView: TextView

    init {
        countryTextView = itemView.findViewById(R.id.country_text)
    }

    fun bind(country: Country) {
        countryTextView.text = country.name
    }
}
