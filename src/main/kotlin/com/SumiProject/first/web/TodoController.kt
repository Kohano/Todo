package com.sumiProject.first.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random


data class Todo(
    val id : Int,
    val title : String
)
data class CreateTodoRequest(
    val title: String
)
@RestController
class TodoController {
    val todoList: MutableList<Todo> = mutableListOf(
        Todo(1,  "Morning Coffee"),
        Todo(2, "Wash up"),
        Todo(3,  "Clean the House"),
        Todo(4,  "Make a Breakfast"),
        Todo(5,  "Play Games"),
        Todo( 6,  "Play Elden Ring"),
        Todo( 7, "Play until All Achievement is obtained "),
        Todo( 8,  "Cry because there is bug in the game"),
        Todo(9,  "Tired from crying so go to sleep"),


    )
    @GetMapping("/todos")
    fun getTodos(): List<Todo> {
        println("Sumi")
        return  todoList
    }

    @PostMapping("/todos")
    fun createTodo(
        @RequestBody request: CreateTodoRequest
    ): Todo {
        println("Received request $request")
        val newTodoId: Int = Random.nextInt(0, 10000)
        val todo = Todo(
            id = newTodoId,
            title = request.title
        )
        todoList.add(todo)
        return todo
    }
}