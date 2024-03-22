package com.sumiproject.first.web

import com.sumiproject.first.domain.Status
import java.time.LocalDateTime

data class CreateTodoRequest(
    val title: String,
    var deadLine: LocalDateTime?,
)

data class UpdateTodoRequest(
    val title: String,
    val status: Status,
    var deadLine: LocalDateTime?,
)

data class TodoResponse(
    val id: Int,
    var title: String,
    var status: Status,
    val createdAt: LocalDateTime,
    var deadLine: LocalDateTime?,
)
