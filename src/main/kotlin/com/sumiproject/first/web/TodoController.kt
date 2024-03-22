package com.sumiProject.first.web

import com.sumiproject.first.domain.TodoService
import com.sumiproject.first.web.CreateTodoRequest
import com.sumiproject.first.web.TodoResponse
import com.sumiproject.first.web.UpdateTodoRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(
    val todoService: TodoService,
) {
    @GetMapping("/todos")
    fun getTodos(): List<TodoResponse> {
        println("Hit the getUsers() endpoint")
        return todoService.getTodos().map {
            TodoResponse(it.id, it.title, it.status, it.createdAt, it.deadLine)
        }
    }

    @GetMapping("/todos/{todoId}")
    fun getTodos(
        @PathVariable todoId: Int,
    ): TodoResponse {
        val todo = todoService.getTodoById(todoId)
        return TodoResponse(todo.id, todo.title, todo.status, todo.createdAt, todo.deadLine)
    }

    @PostMapping("/todos")
    fun createTodo(
        @RequestBody request: CreateTodoRequest,
    ): TodoResponse {
        println("Hit the createUsers() endpoint")
        println("Received request $request")
        val createdTodo = todoService.createTodo(request.title, request.deadLine)
        return TodoResponse(createdTodo.id, createdTodo.title, createdTodo.status, createdTodo.createdAt, createdTodo.deadLine)
    }

    @PatchMapping("/todos/{todoId}")
    fun updateTodo(
        @PathVariable
        todoId: Int,
        @RequestBody
        update: UpdateTodoRequest,
    ): TodoResponse {
        println("Received request $update")
        val updateTodo = todoService.updateTodo(todoId, update.title, update.deadLine, update.status)
        return TodoResponse(updateTodo.id, updateTodo.title, updateTodo.status, updateTodo.createdAt, updateTodo.deadLine)
    }

    @DeleteMapping("/todos/{todoId}")
    fun deleteTodo(
        @PathVariable
        todoId: Int,
    ): TodoResponse {
        val deleteTodo = todoService.deleteTodo(todoId)
        return TodoResponse(deleteTodo.id, deleteTodo.title, deleteTodo.status, deleteTodo.createdAt, deleteTodo.deadLine)
    }
}
