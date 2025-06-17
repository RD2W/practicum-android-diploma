package ru.practicum.android.diploma.region.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.region.domain.model.AreaRegion

class AreaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val areaTextView: TextView

    init {
        areaTextView = itemView.findViewById(R.id.area_text)
    }

    fun bind(area: AreaRegion) {
        areaTextView.text = area.name
    }
}
