package com.sumiProject.first.web

import com.SumiProject.first.errors.BadRequestError
import com.SumiProject.first.errors.NotFoundError
import com.SumiProject.first.web.ErrorResponse
import org.apache.coyote.BadRequestException
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random

enum class Status { InProgress, Ended
}
data class Todo(
    val id : Int,
    var title : String,
    var status: Status = Status.InProgress,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var deadLine : LocalDateTime? = null
)
data class CreateTodoRequest(
    val title: String,
    var deadLine: LocalDateTime?
)
data class UpdateTodoRequest(
    val title: String,
    val status: Status,
    var deadLine: LocalDateTime?
)
@RestController
class TodoController {
    val todoList: MutableList<Todo> = mutableListOf(
        Todo(1,  "Morning Coffee" ),
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
    @GetMapping("/todos/{todoId}")
    fun getTodos(@PathVariable todoId: Int): Todo? {
        println("Path variable received $todoId")
        var resultTodo: Todo? = null
        for(todo in todoList) {
            if (todo.id == todoId) {
                resultTodo = todo
            }
        }
//        val resultTodo = todoList.find { title: User -> title.Id == todoId }
        if(resultTodo == null) throw NotFoundError("User with id $todoId not found!")
        return resultTodo
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