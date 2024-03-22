package com.sumiproject.first.domain

import java.time.LocalDateTime

enum class Status {
    InProgress,
    Ended,
}

data class Todo(
    val id: Int,
    var title: String,
    var status: Status = Status.InProgress,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var deadLine: LocalDateTime? = null,
)
