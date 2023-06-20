package ru.assignment.foodorder.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.assignment.foodorder.dto.Dish

@Entity
@TypeConverters(Converters::class)
data class DishEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val image_url: String,
    val tegs: List<String>,
    val isChosen: Boolean = false,
    val quantity: Int,
) {
    fun toDto() = Dish(
        id,
        name,
        price,
        weight,
        description,
        image_url,
        tegs,
        quantity
    )

    companion object {
        fun fromDto(dto: Dish) = DishEntity(
            dto.id,
            dto.name,
            dto.price,
            dto.weight,
            dto.description,
            dto.image_url,
            dto.tegs,
            quantity = dto.quantity
        )
    }
}
fun List<Dish>.toEntity(): List<DishEntity> = map(DishEntity::fromDto)