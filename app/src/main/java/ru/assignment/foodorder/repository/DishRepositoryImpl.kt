package ru.assignment.foodorder.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import ru.assignment.foodorder.api.ApiService
import ru.assignment.foodorder.dao.DishDao
import ru.assignment.foodorder.dto.Dish
import ru.assignment.foodorder.entity.DishEntity
import ru.assignment.foodorder.entity.toEntity
import java.io.IOException
import javax.inject.Inject

class DishRepositoryImpl @Inject constructor(
    private val dishDao: DishDao,
    private val apiService: ApiService,
) : DishRepository {
    override val data: Flow<List<DishEntity>> = dishDao.getAll()
    override val cartData: Flow<List<DishEntity>> = dishDao.getAll()
        .map{list ->
            list.filter {it.isChosen} }

    override val tags: Flow<List<String>> = dishDao.getAll().map{
        it.flatMap { dish ->
            dish.tegs
        }.toSet().toList()
        }


    override suspend fun getAll() {
        try {
            val response = apiService.getDishes()
            if (!response.isSuccessful) {
                throw Error(response.message())
            }
            val ds = response.body() ?: throw Error(response.message())
            val dishes = ds.values.toList()[0]
            dishDao.insert(dishes.toEntity().map {
                it.copy(quantity = 1)
            })
        } catch (e: IOException) {
            throw e.fillInStackTrace()
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

    override suspend fun getDishById(id: Long): Dish {
        try {
            return dishDao.getDishById(id).toDto()
        } catch (e: IOException) {
            throw e.fillInStackTrace()
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

    override suspend fun choose(dish: Dish) {
        try {
            dishDao.insert(DishEntity.fromDto(dish).copy(isChosen = true))
        } catch (e: IOException) {
            throw e.fillInStackTrace()
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

    override suspend fun clearCart() {
        try {
            dishDao.clearCart()
        } catch (e: IOException) {
            throw e.fillInStackTrace()
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

    override suspend fun changeQuantity(q: Int, id: Long) {
        try {
            dishDao.changeQuantity(q, id)
        } catch (e: IOException) {
            throw e.fillInStackTrace()
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }
}