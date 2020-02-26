package org.example

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.example.api.common.toJson


class ServerException(
    val statusCode: String,
    val errorMessage: String
)

data class ServerBook(
    val id: String,
    val name: String
)

fun main() {
    val server = embeddedServer(Netty, port = 8080) {

        val books = mutableListOf<ServerBook>().also { it += ServerBook("1", "azbuka") }

        install(ContentNegotiation) {
            jackson {}
        }

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
                //also, we can get control of raw text(forward it with minimal effort(hello, spring) and parse as we want)
                //val book = jacksonObjectMapper().readValue(receiveText, Book::class.java)
                val newBook = call.receive(ServerBook::class).also {
                    books += it
                }
                call.respond(newBook)
            }

            post("/book/wrong") {
                val book = call.receive(ServerBook::class)
                with(call) {
                    response.status(BadRequest)
                    respond(ServerException(BadRequest.value.toString(), "wrong book id: $${book.id}"))
                }
            }
        }
    }
    server.start(wait = true)
}
