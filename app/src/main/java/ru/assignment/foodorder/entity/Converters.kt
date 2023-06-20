package ru.assignment.foodorder.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromTagListToJson(value: List<String>) = Gson().toJson(value).toString()

    @TypeConverter
    fun fromJsonToTagList(value: String): List<String> {
        val typeToken = object : TypeToken<List<String>>() {}
        return Gson().fromJson(value, typeToken.type)
    }
}