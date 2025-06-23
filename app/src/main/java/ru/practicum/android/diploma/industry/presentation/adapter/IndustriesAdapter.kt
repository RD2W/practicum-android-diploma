package ru.practicum.android.diploma.industry.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.industry.domain.model.Industry

class IndustriesAdapter : RecyclerView.Adapter<IndustryViewHolder>() {
    var industries = ArrayList<Industry>()
    var onItemClickListener: ((Industry) -> Unit)? = null
    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.industry_view, parent, false)
        return IndustryViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(industries[position])
        holder.setState(position == selectedPosition)
        holder.itemView.setOnClickListener {
            val industry: Industry = industries[position]
            selectedPosition = position
            onItemClickListener?.invoke(industry)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return industries.size
    }

}
