package ru.practicum.android.diploma.region.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.region.domain.model.AreaRegion

class AreasAdapter() : RecyclerView.Adapter<AreaViewHolder>() {

    var areas = ArrayList<AreaRegion>()

    var onItemClickListener: ((AreaRegion) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.area_view, parent, false)
        return AreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(areas[position])
        holder.itemView.setOnClickListener {
            val area: AreaRegion = areas[position]
            onItemClickListener?.invoke(area)
        }
    }

    override fun getItemCount(): Int {
        return areas.size
    }
}
