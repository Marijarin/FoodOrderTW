package ru.assignment.foodorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.assignment.foodorder.dto.Dish
import ru.assignment.foodorder.repository.DishRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: DishRepository
) : ViewModel() {
    private val _payment = MutableStateFlow(0)
    val payment: StateFlow<Int>
        get() = _payment
    val change = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val data: Flow<List<Dish>> = change.flatMapLatest {
        repository.cartData.map { list ->
            if (_payment.value == 0) {
                for (de in list) {
                    _payment.value += de.quantity * de.price
                }
            }
            list.map { dish ->
                dish.toDto()
            }
        }
    }.flowOn(Dispatchers.Default)


    fun changePayment(dish: Dish, q: Int) {
        _payment.value += dish.price * q
    }

    fun changeQuantity(q: Int, id: Long) {
        viewModelScope.launch {
            try {
                repository.changeQuantity(q, id)
            } catch (e: Exception) {
                e.fillInStackTrace()
            }
        }
    }
    fun delete(id: Long){
        viewModelScope.launch {
            try {
                repository.unchoose(id)
            } catch (e: Exception) {
                e.fillInStackTrace()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                repository.clearCart()
                _payment.value = 0
            } catch (e: Exception) {
                e.fillInStackTrace()
            }
        }
    }
}