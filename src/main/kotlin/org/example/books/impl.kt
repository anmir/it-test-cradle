package org.example.books

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.example.ApiException
import org.example.ApiResult
import org.example.ApiResult.Failure
import org.example.ApiResult.Success
import org.example.getOrFail
import java.nio.charset.Charset


class ServiceApiImpl(val mapper: ObjectMapper = jacksonObjectMapper()) : ServiceApi {
    override fun getAllBooks(): ApiResult<List<Book>, ApiException> {
        val (request, response, result) = "http://localhost:8080/books"
            .httpGet()
            .responseString()
        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println(ex)
                return Failure(ApiException(response.statusCode.toString(), "todo"))
            }
            is Result.Success -> {
                val data = result.get()
                println(data)
                val booksType = mapper.typeFactory.constructCollectionType(List::class.java, Book::class.java)
                return ApiResult.success(mapper.readValue(data, booksType))
            }
        }
    }

    override fun getBook(id: String): ApiResult<Book, ApiException> {
        val (request, response, result) = "http://localhost:8080/book/$id"
            .httpGet()
            .responseString()
        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println(ex)
                return Failure(ApiException(response.statusCode.toString(), "todo"))
            }
            is Result.Success -> {
                val data = result.get()
                println(data)
                return ApiResult.success(mapper.readValue(data, Book::class.java))
            }
        }
    }

    override fun addBook(book: Book): ApiResult<Book, ApiException> {
        val (request, response, result) = "http://localhost:8080/book"
            .httpPost()
            .jsonBody(book.toJson())
            .responseString()

        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println(ex)
                return Failure(ApiException(response.statusCode.toString(), "todo"))
            }
            is Result.Success -> {
                val data = result.get()
                println(data)
                return ApiResult.success(mapper.readValue(data, Book::class.java))
            }
        }
    }

    override fun addWrongBook(book: Book): ApiResult<Book, ApiException> {
        val (request, response, result) = "http://localhost:8080/book/wrong"
            .httpPost()
            .jsonBody(book.toJson())
            .responseString()

        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println(ex)
                val apiException =
                    mapper.readValue(response.data.toString(Charset.defaultCharset()), ApiException::class.java)
                return Failure(apiException)
            }
            is Result.Success -> {
                val data = result.get()
                println(data)
                return ApiResult.success(mapper.readValue(data, Book::class.java))
            }
        }
    }
}

fun Any.toJson(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}
