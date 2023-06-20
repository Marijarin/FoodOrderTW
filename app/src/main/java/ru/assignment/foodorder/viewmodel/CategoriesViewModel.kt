package ru.assignment.foodorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.assignment.foodorder.dto.Category
import ru.assignment.foodorder.repository.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    init {
        loadCats()
    }

    val data: Flow<List<Category>> = categoryRepository.data.map {
        it.map { cat ->
            cat.toDto()
        }
    }.flowOn(Dispatchers.Default)

    private fun loadCats() {
        viewModelScope.launch {
            try {
                categoryRepository.getAll()
            } catch (e: Exception) {
                throw e.fillInStackTrace()
            }
        }
    }


}