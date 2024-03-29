package com.sumiproject.first.web

import com.sumiproject.first.errors.BadRequestError
import com.sumiproject.first.errors.NotFoundError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ErrorResponse(
    val status: Int,
    val message: String?,
)

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorResponse> {
        val errorMessage =
            ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.message,
            )
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler
    fun handleNotFountError(ex: NotFoundError): ResponseEntity<ErrorResponse> {
        val errorMessage =
            ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
            )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleBadRequestError(ex: BadRequestError): ResponseEntity<ErrorResponse> {
        val errorMessage =
            ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.message,
            )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}
