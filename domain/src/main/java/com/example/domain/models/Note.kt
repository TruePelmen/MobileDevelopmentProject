package com.example.domain.models

import java.sql.Date

data class Note(
    val id: Long = 0,
    val name: String,
    val text: String,
    var nextRepetition: Date = Date(1648062400000L),
    val lastRepetition: Date = Date(1648062400000L),
    val creationDate: Date = Date(1648062400000L),
    val collectionId: Long
)