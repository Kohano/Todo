package com.sumiProject.first.web

import com.SumiProject.first.Domain.Todo
import com.SumiProject.first.Domain.UserService
import com.SumiProject.first.errors.BadRequestError
import com.SumiProject.first.errors.NotFoundError
import com.SumiProject.first.web.CreateTodoRequest
import com.SumiProject.first.web.ErrorResponse
import com.SumiProject.first.web.TodoResponse
import com.SumiProject.first.web.UpdateTodoRequest
import org.apache.coyote.BadRequestException
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random


@RestController
class TodoController (
    val userService: UserService
){

    @GetMapping("/todos")
    fun getTodos(): List<TodoResponse> {
        println("Hit the getUsers() endpoint")
        return  userService.getTodos().map {
            TodoResponse(it.id, it.title, it.status, it.createdAt, it.deadLine)
        }
    }




    @GetMapping("/todos/{todoId}")
    fun getTodos(@PathVariable todoId: Int): TodoResponse{
        val todo = userService.getTodoById(todoId)
        return TodoResponse(todo.id, todo.title, todo.status, todo.createdAt, todo.deadLine)
    }

    @PostMapping("/todos")
    fun createTodo(
        @RequestBody request: CreateTodoRequest
    ): Todo {
        println("Received request $request")
        val newTodoId: Int = Random.nextInt(0, 10000)
        val todo = Todo(
            id = newTodoId,
            title = request.title,
            deadLine = request.deadLine
        )
        if (request.deadLine != null && request.deadLine!! < LocalDateTime.now()){
            throw BadRequestError("User can not set deadLine on Past. ")
        }


        todoList.add(todo)
        return todo
    }

    @PatchMapping("/todos/{todoId}")
   fun updateTodo(
        @PathVariable
       todoId: Int,

        @RequestBody
       update: UpdateTodoRequest
   ): Todo{
        println("Received request ${update}")
        var existingTodo: Todo? = null
        for(todo in todoList) {
            if (todo.id == todoId) {
                existingTodo = todo
            }
        }

        if (existingTodo == null){
            throw NotFoundError("User with id $todoId not found!")
        }
        println(existingTodo)
         if (update.deadLine != null && update.deadLine!! < LocalDateTime.now()){
             throw BadRequestError("User can not set deadLine on Past. ")
         }
         existingTodo.status = update.status
         existingTodo.deadLine = update.deadLine
         existingTodo.title = update.title
        return existingTodo
   }
    @DeleteMapping("/todos/{todoId}")
    fun delteTodo(
        @PathVariable
        todoId: Int
    ): Todo{
        var existingTodo: Todo? = null
        for(todo in todoList) {
            if (todo.id == todoId) {
                existingTodo = todo
            }

        }
        if (existingTodo == null){
           throw NotFoundError("User with id $todoId not found!")
        }
        todoList.remove(existingTodo)

        return existingTodo
    }



}