package org.example.mockserver

class ServerException(
    val statusCode: String,
    val errorMessage: String
)

data class ServerBook(
    val id: String,
    val name: String
)
