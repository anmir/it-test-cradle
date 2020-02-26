package org.example.api.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import java.nio.charset.Charset

// адаптер между Fuel + jackson -> ApiResult<T, ApiException>

// Можно и вот так сделать, но тогда у нас будут использоваться разные mapper'ы.
// Для data - fuel-jackson,
// Для err  - customDefaultMapper
//public inline fun <reified T : Any> Result<T, FuelError>.toApiResponse(): ApiResult<T, ApiException> {
//    return this.fold({ obj -> ApiResult.success(obj) }, { err -> ApiResult.failure(err.asApiException()) })
//}
val defaultMapper = jacksonObjectMapper()

inline fun <reified T : Any> Result<String, FuelError>.toApiResponse(objectMapper: ObjectMapper = defaultMapper): ApiResult<T, ApiException> {
    return this.fold(
        { obj -> ApiResult.success(obj.deserialize(objectMapper)) },
        { err -> ApiResult.failure(err.asApiException(objectMapper)) }
    )
}

inline fun <reified T> String.deserialize(objectMapper: ObjectMapper): T {
    if (T::class is Collection<*>) {
        val collection = objectMapper.typeFactory.constructCollectionType(List::class.java, T::class.java)
        return objectMapper.readValue(this, collection)
    }
    return objectMapper.readValue(this)
}

fun FuelError.asApiException(objectMapper: ObjectMapper): ApiException {
    return objectMapper.readValue(this.response.data.toString(Charset.defaultCharset()), ApiException::class.java)
}

fun Any.toJson(objectMapper: ObjectMapper = defaultMapper): String {
    return objectMapper.writeValueAsString(this)
}