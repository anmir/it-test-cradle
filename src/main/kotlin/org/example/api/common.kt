package org.example.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import java.nio.charset.Charset

/**
 * common objects, utils, extensions
 * */


open class ApiException(val statusCode: String, val errorMessage: String) : RuntimeException() {
    override fun toString(): String {
        return "ApiException(statusCode='$statusCode', errorMessage='$errorMessage')"
    }
}

sealed class ApiResult<out T : Any, out E : ApiException> {

    data class Success<T : Any>(private val data: T) : ApiResult<T, Nothing>() {
        fun get(): T {
            return this.data
        }
    }

    data class Failure<out E : ApiException>(private val apiException: E) : ApiResult<Nothing, E>() {
        fun getException(): E {
            return this.apiException
        }
    }

    companion object {
        fun <T : Any> success(value: T) = Success(value)
        fun <E : ApiException> failure(value: E) =
            Failure(value)
    }
}

fun <T : Any, E : ApiException> ApiResult<T, E>.getOrFail(): T {
    return when (val ar = this) {
        is ApiResult.Success -> ar.get()
        is ApiResult.Failure -> throw ar.getException()
    }
}

inline fun <reified T : Any> Result<String, FuelError>.toApiResponse(mapper: ObjectMapper): ApiResult<T, ApiException> {
    val result = this
    return when (result) {
        is Result.Failure -> {
            val ex = result.getException()
            println(ex)
            val apiException =
                mapper.readValue(ex.response.data.toString(Charset.defaultCharset()), ApiException::class.java)
            ApiResult.Failure(apiException)
        }
        is Result.Success -> {
            val data = result.get()
            println(data)
            ApiResult.success(mapper.readValue(data, T::class.java))
        }
    }
}
