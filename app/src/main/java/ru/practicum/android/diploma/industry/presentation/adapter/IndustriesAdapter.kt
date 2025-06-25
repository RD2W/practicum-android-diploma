package ru.practicum.android.diploma.industry.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.industry.domain.model.Industry

class IndustriesAdapter : RecyclerView.Adapter<IndustryViewHolder>() {
    var industries = ArrayList<Industry>()
    var onItemClickListener: ((Industry) -> Unit)? = null
    var selectedIndustryId: String? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.industry_view, parent, false)
        return IndustryViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = industries[position]
        holder.bind(industry)
        holder.setState(industry.id == selectedIndustryId)
        holder.itemView.setOnClickListener {
            selectedIndustryId = industry.id
            onItemClickListener?.invoke(industry)
        }
    }

    override fun getItemCount(): Int {
        return industries.size
    }

}
