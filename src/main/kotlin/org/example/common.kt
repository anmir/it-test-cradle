package org.example

open class ApiException(val statusCode: String, val errorMessage: String) : RuntimeException(){
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
        fun <E : ApiException> failure(value: E) = Failure(value)
    }
}

fun <T : Any, E : ApiException> ApiResult<T, E>.getOrFail(): T {
    return when (val ar = this) {
        is ApiResult.Success -> ar.get()
        is ApiResult.Failure -> throw ar.getException()
    }
}