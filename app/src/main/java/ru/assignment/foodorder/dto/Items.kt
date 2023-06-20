package ru.assignment.foodorder.dto

data class Category(
    val id: Long,
    val name: String,
    val image_url: String,
)

data class Dish(
    val id: Long,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val image_url: String,
    val tegs: List<String>,
    val quantity: Int,
)

