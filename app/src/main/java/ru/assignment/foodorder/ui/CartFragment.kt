package ru.assignment.foodorder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.assignment.foodorder.R
import ru.assignment.foodorder.adapter.CartAdapter
import ru.assignment.foodorder.adapter.OnCartItemListener
import ru.assignment.foodorder.databinding.FragmentCartBinding
import ru.assignment.foodorder.dto.Dish
import ru.assignment.foodorder.viewmodel.CartViewModel

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val viewModel: CartViewModel by viewModels()

    companion object {
        private const val Q_PLUS = 1
        private const val Q_MINUS = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCartBinding.inflate(inflater, container, false)
        val adapter = CartAdapter(object : OnCartItemListener {
            override fun onQuantityMinus(dish: Dish) {
                viewModel.changeQuantity(Q_MINUS, dish.id)
                viewModel.changePayment(dish, Q_MINUS)
                viewModel.change.value++

            }

            override fun onQuantityPlus(dish: Dish) {
                viewModel.changeQuantity(Q_PLUS, dish.id)
                viewModel.changePayment(dish, Q_PLUS)
                viewModel.change.value++
            }
        })
        binding.order.adapter = adapter
        lifecycleScope.launchWhenCreated  {
            viewModel.data.collectLatest {
                adapter.submitList(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.payment.collectLatest {
            binding.apply {
                payment.text = getString(R.string.payment, viewModel.payment.value.toString())

            }
        }

        }

        binding.payment.setOnClickListener {
            viewModel.clearCart()
            findNavController().navigate(R.id.action_navigation_cart_to_navigation_main_page)
        }

        return binding.root
    }
}