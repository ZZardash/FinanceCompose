package com.example.financecompose.data.converter

import androidx.room.TypeConverter

object StringToLong {

    @TypeConverter
    @JvmStatic
    fun fromLong(value: Long?): String? {
        return value?.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toLong(value: String?): Long? {
        return value?.toLongOrNull()
    }

}