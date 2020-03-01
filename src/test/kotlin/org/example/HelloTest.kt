package org.example

import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.example.api.books.BooksApi
import org.example.api.books.BooksApiImpl
import org.example.api.common.getOrFail
import org.example.mockserver.MockServerApp
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


class HelloTest {

    private val serviceApi: BooksApi = BooksApiImpl()

    @Test
    @Feature("Get book by id")
    @Severity(SeverityLevel.CRITICAL)
    fun testOutput() {
        val book = serviceApi.getBook("1").getOrFail()
        Assertions.assertNotNull(book)
        Assertions.assertEquals("1", book.id)
        Assertions.assertEquals("azbuka", book.name)
    }

    private companion object {
        private val testServerThread = newSingleThreadContext("test-server")
        @BeforeAll
        @JvmStatic
        fun setUp() {
            CoroutineScope(testServerThread).launch {
                MockServerApp.run()
            }
        }

        @AfterAll
        @JvmStatic
        fun shutdown() {
            CoroutineScope(testServerThread).launch {
                MockServerApp.shutdown()
            }
        }
    }

}
