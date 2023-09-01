package ru.assignment.foodorder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.assignment.foodorder.R
import ru.assignment.foodorder.databinding.CartItemBinding
import ru.assignment.foodorder.dto.Dish

interface OnCartItemListener {
    fun onQuantityMinus(dish: Dish) {}
    fun onQuantityPlus(dish: Dish) {}
    fun onDelete(dish: Dish){}
}

class CartAdapter(
    private val onCartItemListener: OnCartItemListener,
) : ListAdapter<Dish, CartViewHolder>(CartDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onCartItemListener)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val dish = getItem(position)
        holder.bind(dish)
    }

}

class CartViewHolder(
    private val binding: CartItemBinding,
    private val onCartItemListener: OnCartItemListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dish: Dish) {
        binding.apply {
            quantity.text = "${dish.quantity}"
            dishName.text = dish.name
            price.text = "${dish.price}"
            weight.text = "${dish.weight}"
            Glide.with(dishImage)
                .load(dish.image_url)
                .error(R.drawable.twotone_error_outline_24)
                .into(dishImage)
            decrement.setOnClickListener {
                if (dish.quantity > 1) {
                    onCartItemListener.onQuantityMinus(dish)
                    quantity.text.apply { dish.quantity.digitToChar()  }
                } else {
                    root.visibility = View.GONE
                    onCartItemListener.onQuantityMinus(dish)
                    onCartItemListener.onDelete(dish)
                }
            }
            increment.setOnClickListener {
                onCartItemListener.onQuantityPlus(dish)
                quantity.text.apply { dish.quantity.digitToChar() }
            }
        }
    }
}
class CartDiffCallback : DiffUtil.ItemCallback<Dish>() {
    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem == newItem
    }
}