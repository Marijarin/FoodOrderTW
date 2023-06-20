package ru.assignment.foodorder.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsDishRepository(impl: DishRepositoryImpl): DishRepository

    @Singleton
    @Binds
    fun bindsCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}