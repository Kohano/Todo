package com.sumiproject.first.domain

import com.sumiproject.first.errors.BadRequestError
import com.sumiproject.first.errors.NotFoundError
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class TodoService {
    private val todoList: MutableList<Todo> =
        mutableListOf(
//        Todo(1,  "Morning Coffee" ),
//        Todo(2, "Wash up"),
//        Todo(3,  "Clean the House"),
//        Todo(4,  "Make a Breakfast"),
//        Todo(5,  "Play Games"),
//        Todo( 6,  "Play Elden Ring"),
//        Todo( 7, "Play until All Achievement is obtained "),
//        Todo( 8,  "Cry because there is bug in the game"),
//        Todo(9,  "Tired from crying so go to sleep"),
        )

    fun getTodos(): List<Todo> {
        return todoList
    }

    fun getTodoById(todoId: Int): Todo {
        println("Path variable received $todoList")
        var resultTodo: Todo? = null
        for (todo in todoList) {
            if (todo.id == todoId) {
                resultTodo = todo
            }
        }
//        val resultTodo = todoList.find { title: User -> title.Id == todoId }
        if (resultTodo == null) throw NotFoundError("User with id $todoId not found!")
        return resultTodo
    }

    fun createTodo(
        title: String,
        deadLine: LocalDateTime?,
    ): Todo {
        val newTodoId: Int = Random.nextInt(0, 10000)
        val todo =
            Todo(
                id = newTodoId,
                title = title,
                deadLine = deadLine,
            )
        if (deadLine != null && deadLine < LocalDateTime.now()) {
            throw BadRequestError("User can not set deadLine on Past. ")
        }
        todoList.add(todo)
        return todo
    }

    fun updateTodo(
        todoId: Int,
        title: String,
        deadLine: LocalDateTime?,
        status: Status,
    ): Todo {
        var existingTodo: Todo? = null
        for (todo in todoList) {
            if (todo.id == todoId) {
                existingTodo = todo
            }
        }
        if (existingTodo == null) {
            throw NotFoundError("User with id $todoId not found!")
        }
        println(existingTodo)
        if (deadLine != null && deadLine < LocalDateTime.now()) {
            throw BadRequestError("User can not set deadLine on Past. ")
        }
        existingTodo.status = status
        existingTodo.deadLine = deadLine
        existingTodo.title = title
        return existingTodo
    }

    fun deleteTodo(todoId: Int): Todo {
        var existingTodo: Todo? = null
        for (todo in todoList) {
            if (todo.id == todoId) {
                existingTodo = todo
            }
        }
        if (existingTodo == null) {
            throw NotFoundError("User with id $todoId not found!")
        }
        todoList.remove(existingTodo)

        return existingTodo
    }
}
