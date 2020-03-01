package org.example.mockserver

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object MockServerApp {
    private val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) { jackson {} }
        routing {
            ping()
            books()
            delays()
            authors()
        }
    }

    fun run() {
        server.start(wait = true)
    }

    fun shutdown() {
        server.stop(defaultTimeout, defaultTimeout)
    }

    private const val defaultTimeout = 2000L

}

fun main() {
    MockServerApp.run()
}