package com.example.smartnotetaker.domain.models

import java.sql.Date

data class Note(
    val id: Long,
    val name: String,
    val text: String,
    val nextRepetition: Date,
    val lastRepetition: Date,
    val collectionId: Long
)