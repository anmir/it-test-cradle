package org.example.mockserver

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin

object MockServerApp {

    private val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) { jackson {} }
        routing {
            mock()
            ping()
            books()
            delays()
            authors()
        }
    }

    private fun koin() {
        startKoin {
            modules(mockServiceModule)
        }
    }

    fun run() {
        koin()
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