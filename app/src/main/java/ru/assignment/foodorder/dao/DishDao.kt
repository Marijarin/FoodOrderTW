package ru.assignment.foodorder.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.assignment.foodorder.entity.DishEntity

@Dao
interface DishDao {
    @Query("SELECT * FROM DishEntity ORDER BY id DESC")
    fun getAll(): Flow<List<DishEntity>>

    @Query("SELECT * FROM DishEntity WHERE id = :id")
    suspend fun getDishById(id: Long): DishEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: DishEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: List<DishEntity>)

    @Query("""
        UPDATE DishEntity SET
        quantity = quantity + :q
        WHERE id = :id
        """)
    suspend fun changeQuantity(q: Int, id: Long)

    @Query("""
        UPDATE DishEntity SET
        isChosen = 0
    """)
    suspend fun clearCart()

    @Query("""
        UPDATE DishEntity SET
        isChosen = 0
         WHERE id = :id
    """)
    suspend fun unchoose(id: Long)

    @Query("DELETE FROM DishEntity WHERE id = :id")
    suspend fun removeById(id: Long)

    @Query("DELETE FROM DishEntity")
    suspend fun removeAll()

}