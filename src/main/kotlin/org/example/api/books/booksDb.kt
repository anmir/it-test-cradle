package org.example.api.books

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Books : Table("BOOKS") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
}


fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    val books = transaction {
        SchemaUtils.create(Books)
        Books.insert {
            it[id] = 1
            it[name] = "Dictionary"
        }

        Books.selectAll().toList().map { row ->
            Book("${row[Books.id]}", row[Books.name])
        }
    }
    println(books)

}