package org.example.api.books

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import org.example.api.common.*

class BooksApiImpl(
    private val mapper: ObjectMapper = defaultMapper // можем использовать в отдельных сервисах (например, какой-то сервис работает в snake_case)
) : BooksApi {

    override fun getAllBooks(): ApiResult<List<Book>, ApiException> {
        val (_, _, result) = "http://localhost:8080/books"
            .httpGet()
            .responseString()
        return result.toApiResponse()
    }

    override fun getBook(id: String): ApiResult<Book, ApiException> {
        val (_, _, result) = "http://localhost:8080/book/$id"
            .httpGet()
            .responseString()
        return result.toApiResponse()
    }

    override fun addBook(book: Book): ApiResult<Book, ApiException> {
        val (_, _, result) = "http://localhost:8080/book"
            .httpPost()
            .jsonBody(book.toJson())
            .responseString()
        return result.toApiResponse()
    }

    override fun addWrongBook(book: Book): ApiResult<Book, ApiException> {
        val (_, _, result) = "http://localhost:8080/book/wrong"
            .httpPost()
            .jsonBody(book.toJson())
            .responseString()
        return result.toApiResponse()
    }

}