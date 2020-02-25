package org.example.api.books

import org.example.api.ApiException
import org.example.api.ApiResult

interface BooksApi {

    fun getAllBooks(): ApiResult<List<Book>, ApiException>

    fun getBook(id: String): ApiResult<Book, ApiException>

    fun addBook(book: Book): ApiResult<Book, ApiException>

    fun addWrongBook(book: Book): ApiResult<Book, ApiException>

}

data class Book(
    val id: String,
    val name: String
)