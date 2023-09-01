package ru.assignment.foodorder.repository

import kotlinx.coroutines.flow.Flow
import ru.assignment.foodorder.dto.Dish
import ru.assignment.foodorder.entity.DishEntity

interface DishRepository {
    val data: Flow<List<DishEntity>>
    val cartData:Flow<List<DishEntity>>
    suspend fun getAll()
    suspend fun getDishById(id: Long): Dish
    suspend fun choose(dish: Dish)
    suspend fun clearCart()
    suspend fun changeQuantity(q: Int, id: Long)
    suspend fun unchoose(id: Long)
    val tags: Flow<List<String>>
}