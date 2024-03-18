package com.sumiProject.first.web

import com.SumiProject.first.errors.NotFoundError
import com.SumiProject.first.web.ErrorResponse
import org.springframework.web.bind.annotation.*
import kotlin.random.Random


data class Todo(
    val id : Int,
    var title : String
)
data class CreateTodoRequest(
    val title: String
)
data class UpdateTodoRequest(
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
            title = request.title
        )
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