package ru.assignment.foodorder.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.assignment.foodorder.db.AppDb

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun provideDishDao(db: AppDb): DishDao = db.dishDao()

    @Provides
    fun provideCategoryDao(db: AppDb): CategoryDao = db.categoryDao()
}