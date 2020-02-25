package org.example.api.books

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.example.api.ApiException
import org.example.api.ApiResult
import org.example.api.toApiResponse


class BooksApiImpl(val mapper: ObjectMapper = jacksonObjectMapper()) : BooksApi {

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

    //decorate
    private inline fun <reified T : Any> Result<String, FuelError>.toApiResponse(): ApiResult<T, ApiException> {
        return toApiResponse(mapper)
    }
}

//todo: replace with embedded
fun Any.toJson(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}
