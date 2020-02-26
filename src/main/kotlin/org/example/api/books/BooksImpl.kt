package org.example.api.books

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import org.example.api.common.*

class BooksApiImpl(
    private val mapper: ObjectMapper = defaultMapper, // можем использовать в отдельных сервисах (например, какой-то сервис работает в snake_case)
    private val fuel: FuelManager = defaultFuelManager
) : BooksApi {

    override fun getAllBooks(): ApiResult<List<Book>, ApiException> {
        val (_, _, result) = fuel.get("/books")
            .responseString()
        return result.toApiResponse()
    }

    override fun getBook(id: String): ApiResult<Book, ApiException> {
        val (_, _, result) = fuel.get("/book/$id")
            .responseString()
        return result.toApiResponse()
    }

    override fun addBook(book: Book): ApiResult<Book, ApiException> {
        val (_, _, result) = fuel.post("/book")
            .jsonBody(book.toJson())
            .responseString()
        return result.toApiResponse()
    }

    override fun addWrongBook(book: Book): ApiResult<Book, ApiException> {
        val (_, _, result) = fuel.post("/book/wrong")
            .jsonBody(book.toJson())
            .responseString()
        return result.toApiResponse()
    }

    private companion object {
        val defaultFuelManager = FuelManager.instance.also {
            it.basePath = "http://localhost:8080"
        }
    }
}