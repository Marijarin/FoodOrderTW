package ru.assignment.foodorder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.assignment.foodorder.R
import ru.assignment.foodorder.databinding.FragmentDishBinding
import ru.assignment.foodorder.viewmodel.DishesViewModel

@AndroidEntryPoint
class DishFragment : DialogFragment() {
    private val viewModel: DishesViewModel by activityViewModels()
    private var fragmentBinding: FragmentDishBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDishBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding
        val dishId = requireArguments().getLong("dishId")
        lifecycleScope.launchWhenCreated {
            viewModel.getDishById(dishId)
            viewModel.dish.collectLatest { dish ->
                binding.apply {
                    Glide.with(dishImage)
                        .load(dish.image_url)
                        .error(R.drawable.twotone_error_outline_24)
                        .into(dishImage)
                    dishName.text = dish.name
                    price.text = dish.price.toString()
                    weight.text = dish.weight.toString()
                    description.text = dish.description
                }
            }
        }
        binding.apply {
            addToCart.setOnClickListener {
                viewModel.choose(viewModel.dish.value)
                close.setOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}