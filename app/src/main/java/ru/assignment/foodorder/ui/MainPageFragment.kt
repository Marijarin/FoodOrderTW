package ru.assignment.foodorder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.assignment.foodorder.R
import ru.assignment.foodorder.adapter.CategoryAdapter
import ru.assignment.foodorder.adapter.OnCategoryListener
import ru.assignment.foodorder.databinding.FragmentMainPageBinding
import ru.assignment.foodorder.dto.Category
import ru.assignment.foodorder.viewmodel.CategoriesViewModel

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainPageBinding.inflate(inflater, container, false)
        val adapter = CategoryAdapter(object : OnCategoryListener {
            override fun onCatClick(category: Category) {
                findNavController().navigate(R.id.action_navigation_main_page_to_dishFragment, bundleOf("cat" to category.name))
            }
        })
        binding.categories.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest(adapter::submitList)
        }

        /*requireActivity().addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.cat_options_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }

        }, viewLifecycleOwner)*/

        return binding.root
    }

}