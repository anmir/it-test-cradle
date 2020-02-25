package org.example.api

import org.example.api.books.Book
import org.example.api.books.BooksApiImpl

// элементы сценариев
fun main(args: Array<String>) {
    val serviceApi = BooksApiImpl()

    val book = serviceApi.getBook("1").getOrFail()
    println("get one : $book")

    val newBook = serviceApi.addBook(Book("2", "Znaika")).getOrFail()
    println("add second : $newBook")

    val allBooks = serviceApi.getAllBooks().getOrFail()
    println("list all : $allBooks")

    val wrongBook = serviceApi.addWrongBook(Book("3", "Вредные советы!"))
    when (wrongBook) {
        is ApiResult.Success -> println("no no no! It's impossible... Must....")
        is ApiResult.Failure -> println("I've got that exception! I can analyze it later...: ${wrongBook.getException()}")
    }

}