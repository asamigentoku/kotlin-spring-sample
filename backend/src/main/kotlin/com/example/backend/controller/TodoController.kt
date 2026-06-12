package com.example.backend.controller

import com.example.backend.model.TodoRequest
import com.example.backend.model.TodoResponse
import com.example.backend.repository.TodoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoRepository: TodoRepository) {

    @GetMapping
    fun findAll(): List<TodoResponse> = todoRepository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<TodoResponse> =
        todoRepository.findById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: TodoRequest): TodoResponse =
        todoRepository.create(request.title, request.completed ?: false)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: TodoRequest): ResponseEntity<TodoResponse> =
        todoRepository.update(id, request.title, request.completed ?: false)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> =
        if (todoRepository.delete(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
}
