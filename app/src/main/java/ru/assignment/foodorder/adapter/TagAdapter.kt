package ru.assignment.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.assignment.foodorder.databinding.TagItemBinding

interface OnTagListener {
    fun onTagClick(tag: String) {}
}

class TagAdapter(
    private val onTagListener: OnTagListener,
) : ListAdapter<String, TagViewHolder>(StringDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding =
            TagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding, onTagListener)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = getItem(position)
        holder.bind(tag)
    }

}

class TagViewHolder(
    private val binding: TagItemBinding,
    private val onTagListener: OnTagListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tag: String) {
        binding.apply {
            tagButton.text = tag
            tagButton.setOnClickListener { onTagListener.onTagClick(tag) }
        }
    }
}

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}



