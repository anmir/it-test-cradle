package org.example

import com.github.kittinunf.fuel.httpGet
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.example.api.books.BooksApi
import org.example.api.books.BooksApiImpl
import org.example.api.common.getOrFail
import org.example.mockserver.MockServerApp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS


@EnabledOnOs(OS.WINDOWS)
class BooksTest {

    private val serviceApi: BooksApi = BooksApiImpl()

    @Test
    @DisplayName("ping mock сервера")
    @Severity(SeverityLevel.CRITICAL)
    fun testPing() {
        val (_, response, _) = "http://localhost:8080/ping".httpGet().responseString()
        Assertions.assertEquals(200, response.statusCode)
    }

    @Test
    @DisplayName("Получение книг по id")
    @Severity(SeverityLevel.CRITICAL)
    fun testGetBookById() {
        val book = serviceApi.getBook("1").getOrFail()
        Assertions.assertNotNull(book)
        Assertions.assertEquals("1", book.id)
        Assertions.assertEquals("azbuka", book.name)
    }

    private companion object {

        @ObsoleteCoroutinesApi
        private val testServerThread = newSingleThreadContext("test-server")

        @ObsoleteCoroutinesApi
        @BeforeAll
        @JvmStatic
        fun setUp() {
            CoroutineScope(testServerThread).launch {
                MockServerApp.run()
            }
        }

        @ObsoleteCoroutinesApi
        @AfterAll
        @JvmStatic
        fun shutdown() {
            CoroutineScope(testServerThread).launch {
                MockServerApp.shutdown()
            }
        }
    }

}
