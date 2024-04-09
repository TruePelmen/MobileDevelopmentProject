package com.example.data

import androidx.room.TypeConverter
import java.sql.Date

class SqlDateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return timeStamp?.let { Date(it) }
    }
}