package ru.assignment.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.assignment.foodorder.R
import ru.assignment.foodorder.databinding.CategoryItemBinding
import ru.assignment.foodorder.databinding.CategoryItemBinding.inflate
import ru.assignment.foodorder.dto.Category

interface OnCategoryListener {
    fun onCatClick(category: Category) {}
}

class CategoryAdapter(
    private val onCategoryListener: OnCategoryListener,
) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategoryListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

}

class CategoryViewHolder(
    private val binding: CategoryItemBinding,
    private val onCategoryListener: OnCategoryListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category) {
        binding.apply {
            categoryName.text = category.name
            Glide.with(catImage)
                .load(category.image_url)
                .error(R.drawable.twotone_error_outline_24)
                .into(catImage)
            catImage.setOnClickListener {
                onCategoryListener.onCatClick(category)
            }
        }
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}
