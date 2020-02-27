package org.example.mockserver

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) { jackson {} }
        routing {
            ping()
            books()
            delays()
            authors()
        }
    }
    server.start(wait = true)
}

