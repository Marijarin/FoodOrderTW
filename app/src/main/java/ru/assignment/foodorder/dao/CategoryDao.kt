package ru.assignment.foodorder.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.assignment.foodorder.entity.CategoryEntity
import ru.assignment.foodorder.entity.DishEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity ORDER BY id DESC")
    fun getAll(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<CategoryEntity>)

    @Query("DELETE FROM CategoryEntity")
    suspend fun removeAll()
}