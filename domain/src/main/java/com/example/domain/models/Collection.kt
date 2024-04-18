package com.example.domain.models

import java.sql.Date

data class Collection(
    val id: Long=0,
    val name: String,
    val creationDate: Date = Date(1648062400000L)
)