package org.example.api.authors

import org.example.api.common.ApiException
import org.example.api.common.ApiResult

interface AuthorsApi {

    fun getAllAuthors(): ApiResult<List<Author>, ApiException>

    fun getAuthor(id: String): ApiResult<Author, ApiException>

}

data class Author(
    val id: String,
    val name: String
)