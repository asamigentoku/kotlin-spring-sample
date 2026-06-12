package com.example.backend.model

import java.time.LocalDateTime

data class TodoRequest(
    val title: String,
    val completed: Boolean? = null,
)

data class TodoResponse(
    val id: Int,
    val title: String,
    val completed: Boolean,
    val createdAt: LocalDateTime,
)
