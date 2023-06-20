package ru.assignment.foodorder.api

import retrofit2.Response
import retrofit2.http.GET
import ru.assignment.foodorder.dto.Category
import ru.assignment.foodorder.dto.Dish

interface ApiService{
@GET("058729bd-1402-4578-88de-265481fd7d54")
suspend fun getCategories(): Response<Map<String,List<Category>>>

@GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
suspend fun getDishes(): Response<Map<String, List<Dish>>>
}