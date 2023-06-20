package ru.assignment.foodorder.model

import ru.assignment.foodorder.dto.Dish

data class FeedModel(
    val dishes: List<Dish> = emptyList(),
    val empty: Boolean = false,
)

sealed interface FeedModelState {
    object Error : FeedModelState
    object Refreshing : FeedModelState
    object Loading : FeedModelState
    object Idle : FeedModelState
}