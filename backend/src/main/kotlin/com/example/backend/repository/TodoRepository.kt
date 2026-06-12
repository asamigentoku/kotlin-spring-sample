package com.example.backend.repository

import com.example.backend.db.Todos
import com.example.backend.model.TodoResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TodoRepository {

    private fun toResponse(row: ResultRow) = TodoResponse(
        id = row[Todos.id],
        title = row[Todos.title],
        completed = row[Todos.completed],
        createdAt = row[Todos.createdAt],
    )

    fun findAll(): List<TodoResponse> = transaction {
        Todos.selectAll().orderBy(Todos.id to SortOrder.ASC).map(::toResponse)
    }

    fun findById(id: Int): TodoResponse? = transaction {
        Todos.selectAll().where { Todos.id eq id }.map(::toResponse).singleOrNull()
    }

    fun create(title: String, completed: Boolean): TodoResponse = transaction {
        val id = Todos.insert {
            it[Todos.title] = title
            it[Todos.completed] = completed
            it[Todos.createdAt] = LocalDateTime.now()
        } get Todos.id
        findById(id)!!
    }

    fun update(id: Int, title: String, completed: Boolean): TodoResponse? = transaction {
        val updated = Todos.update({ Todos.id eq id }) {
            it[Todos.title] = title
            it[Todos.completed] = completed
        }
        if (updated > 0) findById(id) else null
    }

    fun delete(id: Int): Boolean = transaction {
        Todos.deleteWhere { Todos.id eq id } > 0
    }
}
