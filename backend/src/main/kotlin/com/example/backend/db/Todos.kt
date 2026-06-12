package com.example.backend.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Todos : Table("todos") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val completed = bool("completed").default(false)
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}
