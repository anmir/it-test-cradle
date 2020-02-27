package org.example.api.common

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.runBlocking

// адаптер между Fuel + jackson -> ApiResult<T, ApiException>

//внутри CoroutineScope есть проблемы с выодом типа. поэтому сделано блокирующее чтение ответа
inline fun <reified T : Any> HttpResponse.toApiResponse(mapper: ObjectMapper = defaultMapper): ApiResult<T, ApiException> {
    val response = this
    return when (val statusCode = response.status.value) {
        in 200..299 -> {
            val readText = runBlocking { response.readText() }
            ApiResult.success(readText.deserialize(mapper))
        }
        in 400..599 -> {
            val readText = runBlocking { response.readText() }
            ApiResult.failure(readText.deserialize(mapper))
        }
        else -> throw RuntimeException("Ну и статусы у вас... $statusCode")
    }
}

suspend inline fun <reified T : Any> HttpResponse.toApiResponseSuspend(mapper: ObjectMapper = defaultMapper): ApiResult<T, ApiException> {
    val response = this
    return when (val statusCode = response.status.value) {
        in 200..299 -> {
            val readText =  response.readText()
            ApiResult.success(readText.deserialize(mapper))
        }
        in 400..599 -> {
            val readText = response.readText()
            ApiResult.failure(readText.deserialize(mapper))
        }
        else -> throw RuntimeException("Ну и статусы у вас... $statusCode")
    }
}
