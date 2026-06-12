package com.example.backend.config

import com.example.backend.db.Todos
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig(private val dataSource: DataSource) {

    @PostConstruct
    fun init() {
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(Todos)
        }
    }
}
