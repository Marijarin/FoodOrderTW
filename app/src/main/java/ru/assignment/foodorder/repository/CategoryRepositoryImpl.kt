package ru.assignment.foodorder.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.assignment.foodorder.api.ApiService
import ru.assignment.foodorder.dao.CategoryDao
import ru.assignment.foodorder.dto.Category
import ru.assignment.foodorder.entity.CategoryEntity
import ru.assignment.foodorder.entity.toEntity
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val apiService: ApiService,
) : CategoryRepository {
    override val data: Flow<List<CategoryEntity>> = categoryDao.getAll().flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try {
            val response = apiService.getCategories()
            if (!response.isSuccessful) {
                throw Error(response.message())
            }
            val cats = response.body() ?: throw Error(response.message())
            val categories = cats.values.toList()[0]
            categoryDao.insert(categories.toEntity())
        } catch (e: IOException) {
            throw e.fillInStackTrace()
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

}