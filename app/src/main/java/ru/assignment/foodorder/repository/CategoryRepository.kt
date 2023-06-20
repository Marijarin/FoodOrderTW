package ru.assignment.foodorder.repository

import kotlinx.coroutines.flow.Flow
import ru.assignment.foodorder.dto.Category
import ru.assignment.foodorder.entity.CategoryEntity

interface CategoryRepository {
    val data: Flow<List<CategoryEntity>>
    suspend fun getAll()

}