package org.example.mockserver

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import kotlinx.coroutines.delay
import org.example.api.authors.Author
import org.example.api.common.toJson

fun Routing.ping() {
    get("/ping") {
        call.respondText("""{"status": "up"}""", ContentType.Application.Json)
    }
}

fun Routing.books() {
    val books = mutableListOf<ServerBook>().also { it += ServerBook(
        "1",
        "azbuka"
    )
    }
    get("/book/{id}") {
        val bookId = call.parameters["id"]
        call.respondText(books.find { it.id == bookId }!!.toJson())
    }

    get("/books") {
        call.respondText(books.toJson())
    }

    post("/book") {
        val newBook = call.receive(ServerBook::class).also {
            books += it
        }
        call.respond(newBook)
    }

    post("/book/wrong") {
        val book = call.receive(ServerBook::class)
        with(call) {
            response.status(HttpStatusCode.BadRequest)
            respond(
                ServerException(
                    HttpStatusCode.BadRequest.value.toString(),
                    "wrong book id: $${book.id}"
                )
            )
        }
    }
}



fun Routing.delays() {
    get("/one") {
        delay(1000)
        call.respondText("good", ContentType.Text.Plain)
    }

    get("/two") {
        delay(2000)
        call.respondText("bad", ContentType.Text.Plain)
    }
    get("/three") {
        delay(3000)
        call.respondText("good", ContentType.Text.Plain)
    }
}

fun Routing.authors() {
    val authors = mutableListOf<Author>().also { it += Author("1", "Lev Tolstoy") }
    get("/authors/{id}") {
        val authorId = call.parameters["id"]
        call.respond(authors.find { it.id == authorId }!!)
    }

    get("/authors") {
        call.respondText(authors.toJson())
    }

    post("/authors") {
        val newAuthor = call.receive(Author::class).also {
            authors += it
        }
        call.respond(newAuthor)
    }

    post("/authors/wrong") {
        val author = call.receive(Author::class)
        with(call) {
            response.status(HttpStatusCode.BadRequest)
            respond(
                ServerException(
                    HttpStatusCode.BadRequest.value.toString(),
                    "wrong author id: $${author.id}"
                )
            )
        }
    }
}