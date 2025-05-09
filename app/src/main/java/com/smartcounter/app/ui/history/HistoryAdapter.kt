package com.smartcounter.app.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartcounter.app.data.model.Count
import com.smartcounter.app.databinding.ItemCountBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<Count, HistoryAdapter.CountViewHolder>(CountDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountViewHolder {
        val binding = ItemCountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CountViewHolder(
        private val binding: ItemCountBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

        fun bind(count: Count) {
            binding.apply {
                countTextView.text = count.count.toString()
                dateTextView.text = dateFormat.format(count.timestamp)
                sessionTypeTextView.text = if (count.isGroupSession) "Group Session" else "Solo Session"
            }
        }
    }
}

class CountDiffCallback : DiffUtil.ItemCallback<Count>() {
    override fun areItemsTheSame(oldItem: Count, newItem: Count): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Count, newItem: Count): Boolean {
        return oldItem == newItem
    }
} 