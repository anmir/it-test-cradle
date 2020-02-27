package org.example.api.authors

// элементы сценариев
fun main() {
    val defaultKtorClient = defaultKtorClient()

    val serviceApi = AuthorsImpl(defaultKtorClient)
    defaultKtorClient.use {
        val author = serviceApi.getAuthor("1")
        println("get one : $author")

        val allAuthors = serviceApi.getAllAuthors()
        println("list all : $allAuthors")
    }


//    val newBook = serviceApi.addBook(Book("2", "Znaika")).getOrFail()
//    println("add second : $newBook")

//    val wrongBook = serviceApi.addWrongBook(Book("3", "Вредные советы!"))
//    when (wrongBook) {
//        is ApiResult.Success -> println("no no no! It's impossible... Must....")
//        is ApiResult.Failure -> println("I've got that exception! I can analyze it later...: ${wrongBook.getException()}")
//    }

}