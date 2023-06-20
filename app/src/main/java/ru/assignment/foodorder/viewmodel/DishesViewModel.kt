package ru.assignment.foodorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.assignment.foodorder.dto.Dish
import ru.assignment.foodorder.repository.DishRepository
import javax.inject.Inject

val empty = Dish(
    id = 0,
    name = " ",
    price = 0,
    weight = 0,
    description = " ",
    image_url = " ",
    tegs = emptyList(),
    quantity = 1,
)

@HiltViewModel
class DishesViewModel @Inject constructor(
    private val repository: DishRepository,
) : ViewModel() {

    init {
        loadDishes()
    }
    private val chosenTag = MutableStateFlow("Все меню")

    fun chooseTag(tag: String){
        chosenTag.value = tag
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    val data: Flow<List<Dish>> = chosenTag.flatMapLatest {
        repository.data.map { list->
            list.map { dish -> dish.toDto() }.filter { it.tegs.contains(chosenTag.value) }
        }.flowOn(Dispatchers.Default)
    }

    val tags: Flow<List<String>> = repository.tags.flowOn(Dispatchers.Default)

    private val _dish = MutableStateFlow(empty)
    val dish: StateFlow<Dish>
        get() = _dish

    private fun loadDishes() {
        viewModelScope.launch {
            try {
                repository.getAll()
            } catch (e: Exception) {
                throw e.fillInStackTrace()
            }
        }
    }
    fun getDishById(id: Long){
        viewModelScope.launch {
            try {
                _dish.value = repository.getDishById(id)
            } catch (e: Exception) {
                throw e.fillInStackTrace()
            }
        }
    }

    fun choose(dish: Dish) {
        viewModelScope.launch {
            try {
                repository.choose(dish)
            } catch (e: Exception) {
                throw e.fillInStackTrace()
            }
        }
    }
}