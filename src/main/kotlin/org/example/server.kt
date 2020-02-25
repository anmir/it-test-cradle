package org.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.example.books.Book
import org.example.books.toJson

class ServerException(
    val statusCode: String,
    val errorMessage: String
)

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        val books = mutableListOf<Book>()
        books += Book("1", "azbuka")
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/book/{id}") {
                val bookId = call.parameters["id"]
                call.respondText(books.find { it.id == bookId }!!.toJson())
            }
            get("/books") {
                call.respondText(books.toJson())
            }

            post("/book") {
                val receiveText = call.receiveText()
                val book = jacksonObjectMapper().readValue(receiveText, Book::class.java)
                books += book
                call.respondText(book.toJson())
            }
            post("/book/wrong") {
                val receiveText = call.receiveText()
                val book = jacksonObjectMapper().readValue(receiveText, Book::class.java)
                with(call) {
                    response.status(BadRequest)
                    respondText(
                        """
                    {
                    "statusCode": "$BadRequest",
                    "errorMessage": "wrong book id: $${book.id}"
                    }
                """.trimIndent()
                    )
                }
            }
        }
    }
    server.start(wait = true)
}
