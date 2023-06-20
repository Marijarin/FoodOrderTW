package ru.assignment.foodorder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.assignment.foodorder.dao.CategoryDao
import ru.assignment.foodorder.dao.DishDao
import ru.assignment.foodorder.entity.CategoryEntity
import ru.assignment.foodorder.entity.DishEntity

@Database(entities = [DishEntity::class, CategoryEntity::class], version = 1, exportSchema = false)

abstract class AppDb: RoomDatabase() {
abstract fun dishDao(): DishDao
abstract fun categoryDao(): CategoryDao
}