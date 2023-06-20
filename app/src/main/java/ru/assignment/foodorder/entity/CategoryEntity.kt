package ru.assignment.foodorder.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.assignment.foodorder.dto.Category
import ru.assignment.foodorder.dto.Dish

@Entity
data class CategoryEntity (
    @PrimaryKey
    val id: Long,
    val name: String,
    val image_url: String,
){
    fun toDto() = Category(
        id,
        name,
        image_url,
    )

    companion object {
        fun fromDto(dto: Category) = CategoryEntity(
            dto.id,
            dto.name,
            dto.image_url,
        )
    }
}
fun List<Category>.toEntity(): List<CategoryEntity> = map(CategoryEntity::fromDto)