package com.SumiProject.first.Domain

import com.SumiProject.first.errors.NotFoundError
import org.springframework.stereotype.Service

@Service
class UserService{
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
    fun getTodos(): List<Todo> {

        return todoList
    }
    fun getTodoById(TodoId: Int): Todo{

            println("Path variable received $todoList")
            var resultTodo: Todo? = null
            for(todo in todoList) {
                if (todo.id == TodoId) {
                    resultTodo = todo
                }
            }
//        val resultTodo = todoList.find { title: User -> title.Id == todoId }
            if(resultTodo == null) throw NotFoundError("User with id $TodoId not found!")
            return resultTodo

    }
    fun
}