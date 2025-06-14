package ru.practicum.android.diploma.industry.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.industry.domain.model.Industry

class IndustryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val industryRadioImageView: ImageView
    private val industryTextView: TextView

    init {
        industryRadioImageView = itemView.findViewById(R.id.industry_radio_image)
        industryTextView = itemView.findViewById(R.id.industry_text)
    }

    fun bind(industry: Industry) {
        industryTextView.text = industry.name
    }

    fun setState(isChecked: Boolean) {
        if (isChecked) {
            industryRadioImageView.setImageResource(R.drawable.radio_button_on)
        } else {
            industryRadioImageView.setImageResource(R.drawable.ic_radio_button_off)
        }
    }
}
