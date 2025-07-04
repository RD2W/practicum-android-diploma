package ru.practicum.android.diploma.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.model.Vacancy
import ru.practicum.android.diploma.common.presentation.adapter.VacancyViewHolder
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import kotlin.math.max

class VacancyLoadMoreAdapter(
    private val onVacancyClick: (Vacancy) -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private var isLoadingFooterAdded = false

    private var loadingStartTime: Long = 0
    private var loadingJob: Job? = null

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.LoadingItem -> VIEW_TYPE_LOADING
            is ListItem.VacancyItem -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        } else {
            val binding = ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VacancyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.VacancyItem -> {
                (holder as VacancyViewHolder).bind(item.vacancy)
                holder.itemView.setOnClickListener { onVacancyClick(item.vacancy) }
            }

            is ListItem.LoadingItem -> {
                // ProgressBar крутится сам :)
            }
        }
    }

    fun addLoadingFooter() {
        if (!isLoadingFooterAdded) {
            isLoadingFooterAdded = true
            loadingStartTime = System.currentTimeMillis()
            val newList = currentList.toMutableList()
            newList.add(ListItem.LoadingItem)
            submitList(newList)
        }
    }

    fun removeLoadingFooter() {
        if (isLoadingFooterAdded) {
            loadingJob?.cancel()
            loadingJob = CoroutineScope(Dispatchers.Main).launch {
                val elapsedTime = System.currentTimeMillis() - loadingStartTime
                val remainingTime = max(ZERO_MS, MIN_LOADING_TIME - elapsedTime)

                if (remainingTime > ZERO_MS) {
                    delay(remainingTime)
                }

                isLoadingFooterAdded = false
                val newList = currentList.toMutableList()
                val index = newList.indexOfLast { it is ListItem.LoadingItem }
                if (index != -1) {
                    newList.removeAt(index)
                }
                submitList(newList)
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        loadingJob?.cancel()
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val ZERO_MS = 0L
        private const val MIN_LOADING_TIME = 500L // Минимальное время показа ProgressBar (500 мс)
    }

}
