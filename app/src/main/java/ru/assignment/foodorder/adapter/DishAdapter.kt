package ru.assignment.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.assignment.foodorder.R
import ru.assignment.foodorder.databinding.DishItemSmallBinding
import ru.assignment.foodorder.dto.Dish

interface OnDishListener {
    fun onDishClick(dish: Dish) {}
}

class DishAdapter(
    private val onDishListener: OnDishListener,
) : ListAdapter<Dish, DishViewHolder>(DishDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding =
            DishItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding, onDishListener)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = getItem(position)
        holder.bind(dish)
    }

}

class DishViewHolder(
    private val binding: DishItemSmallBinding,
    private val onDishListener: OnDishListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dish: Dish) {
        binding.apply {
            dishName.text = dish.name
            Glide.with(dishImage)
                .load(dish.image_url)
                .error(R.drawable.twotone_error_outline_24)
                .into(dishImage)
            dishImage.setOnClickListener {
                onDishListener.onDishClick(dish)
            }
        }
    }
}

class DishDiffCallback : DiffUtil.ItemCallback<Dish>() {
    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem == newItem
    }
}


