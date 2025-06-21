package ru.practicum.android.diploma.country.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.country.domain.model.Country

class CountriesAdapter : RecyclerView.Adapter<CountryViewHolder>() {

    var countries = ArrayList<Country>()

    var onItemClickListener: ((Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_view, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.itemView.setOnClickListener {
            val country: Country = countries[position]
            onItemClickListener?.invoke(country)
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}
