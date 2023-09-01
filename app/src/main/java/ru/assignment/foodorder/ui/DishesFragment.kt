package ru.assignment.foodorder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.assignment.foodorder.R
import ru.assignment.foodorder.adapter.DishAdapter
import ru.assignment.foodorder.adapter.OnDishListener
import ru.assignment.foodorder.adapter.OnTagListener
import ru.assignment.foodorder.adapter.TagAdapter
import ru.assignment.foodorder.databinding.FragmentDishesBinding
import ru.assignment.foodorder.dto.Dish

import ru.assignment.foodorder.viewmodel.DishesViewModel

@AndroidEntryPoint
class DishesFragment : Fragment() {
    private val viewModel: DishesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDishesBinding.inflate(inflater, container, false)
        val cat = arguments?.getString("cat")
        binding.chosenCategory.text = cat
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        val adapter = DishAdapter(object : OnDishListener {
            override fun onDishClick(dish: Dish) {
                findNavController().navigate(
                    R.id.action_dishFragment_to_dishFragment2,
                    bundleOf("dishId" to dish.id)
                )
            }
        })
        val tgAdapter = TagAdapter(object : OnTagListener {
            override fun onTagClick(tag: String) {
                viewModel.chooseTag(tag)
            }
        })
        binding.dishes.adapter = adapter
        binding.tags.adapter = tgAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.tags.collectLatest { tgAdapter.submitList(it) }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest(adapter::submitList)
        }

        return binding.root
    }

}