package org.example.books

import org.example.ApiException
import org.example.ApiResult

interface ServiceApi {

    fun getAllBooks(): ApiResult<List<Book>, ApiException>

    fun getBook(id: String): ApiResult<Book, ApiException>

    fun addBook(book: Book): ApiResult<Book, ApiException>

    fun addWrongBook(book: Book): ApiResult<Book, ApiException>

}

data class Book(
    val id: String,
    val name: String
)