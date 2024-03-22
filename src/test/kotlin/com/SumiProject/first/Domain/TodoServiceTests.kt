package com.SumiProject.first.Domain

import com.SumiProject.first.errors.BadRequestError
import com.SumiProject.first.errors.NotFoundError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TodoServiceTests{
    @Test
    fun `getTodos() should return the existing todos`(){
//        give
        val todoService = TodoService()
//        when
        val actualResult = todoService.getTodos()
//        then
        val expectedResult : List<Todo> = listOf()

        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun `getTodoById() should return the requested todo`() {
        // given
        val todoService = TodoService()
        val createdTodo = todoService.createTodo("Wash up" , null)

        // when
        val actualResult = todoService.getTodoById(createdTodo.id)

        // then
        val expectedResult = createdTodo
        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun `getTodoById() should return the requested todos`() {
        // given
        val todoService = TodoService()
        val noneExistingTodo= 5

        // then
        org.junit.jupiter.api.assertThrows<NotFoundError> {
            // when
            todoService.getTodoById(noneExistingTodo)
        }
    }
    @Test
    fun`createTodo() should return the created todo`(){
//        give
        val todoService = TodoService()

//        then
        val actualResult = todoService.createTodo("Wash up" , deadLine = null)

//        then
        val expectedTitle ="Wash up"
        val expectedDeadLine = null
        val exceptedStatus = Status.InProgress
        assertEquals(expectedTitle,actualResult.title)
        assertEquals(expectedDeadLine,actualResult.deadLine)
        assertEquals(exceptedStatus,actualResult.status)
    }
    @Test
    fun `createTodo() should return the Error when deadline in the past was`() {
        // given
        val todoService = TodoService()
        val pastTime:LocalDateTime = LocalDateTime.now()

        // then
        org.junit.jupiter.api.assertThrows<BadRequestError> {
            // when
            todoService.createTodo(title = "Wash up",pastTime)
        }
    }
    @Test
    fun `updateTodo() should return the updated todo`(){
//        give
        val todoService = TodoService()
        val createTodo = todoService.createTodo(title ="Wash up", deadLine = null)
//        when
        val actualResult = todoService.updateTodo(createTodo.id,"Wash up",null,Status.InProgress)
//        then
        val exceptedId = createTodo.id
        val exceptedTitle = "Wash up"
        val exceptedDeadLine = null
        val exceptedStatus = actualResult.status
        assertEquals(exceptedId,actualResult.id)
        assertEquals(exceptedTitle,actualResult.title)
        assertEquals(exceptedDeadLine,actualResult.deadLine)
        assertEquals(exceptedStatus,actualResult.status)
    }
    @Test
    fun `updateTodo() should return the Error when deadline in the past was`(){
        // given
        val todoService = TodoService()
        val createTodo = todoService.createTodo(title ="Wash up", deadLine = null)
        val pastTime:LocalDateTime = LocalDateTime.now()

        // then
        org.junit.jupiter.api.assertThrows<BadRequestError> {
            // when
            todoService.updateTodo(createTodo.id,createTodo.title,pastTime,createTodo.status)
        }
    }
    @Test
    fun `updateTodo() should return the Error when id is not found`(){
        // given
        val todoService = TodoService()
        val createTodo = todoService.createTodo(title ="Wash up", deadLine = null)
        val noneExistingTodo= 10

        // then
        org.junit.jupiter.api.assertThrows<NotFoundError> {
            // when
            todoService.updateTodo(noneExistingTodo,createTodo.title,null,createTodo.status)
        }
    }
    @Test
    fun `deleteTodo() should return the deleted `(){
//        give
        val todoService = TodoService()
        val createTodo = todoService.createTodo(title ="Wash up", deadLine = null)
//        when
        val actualResult = todoService.deleteTodo(createTodo.id)
        val remainningTodos = todoService.getTodos()
//        then
        val exceptedId = createTodo.id
        assertEquals(exceptedId,actualResult.id)
        assertEquals(remainningTodos.isEmpty(),false)

    }
    @Test
    fun `deleteTodo() should return the Error when id is not found`(){
        // given
        val todoService = TodoService()
        val createTodo = todoService.createTodo(title ="Wash up", deadLine = null)
        val noneExistingTodo= 10

        // then
        org.junit.jupiter.api.assertThrows<NotFoundError> {
            // when
            todoService.deleteTodo(noneExistingTodo)
        }
    }


}