package com.SumiProject.first.web

import com.SumiProject.first.Domain.Status
import java.time.LocalDateTime

data class CreateTodoRequest(
    val title: String,
    var deadLine: LocalDateTime?
)
data class UpdateTodoRequest(
    val title: String,
    val status: Status,
    var deadLine: LocalDateTime?
)
data class TodoResponse(
    val id : Int,
    var title : String,
    var status: Status = Status.InProgress,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var deadLine : LocalDateTime? = null
)
