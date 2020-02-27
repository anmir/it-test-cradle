package org.example.api.common

import com.fasterxml.jackson.databind.ObjectMapper
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

inline fun <reified T : Any> Result<String, FuelError>.toApiResponse(objectMapper: ObjectMapper = defaultMapper): ApiResult<T, ApiException> {
    return this.fold(
        { responseString -> ApiResult.success(responseString.deserialize(objectMapper)) },
        { errorString -> ApiResult.failure(errorString.asApiException(objectMapper)) }
    )
}

fun FuelError.asApiException(objectMapper: ObjectMapper): ApiException {
    return objectMapper.readValue(this.response.data.toString(Charset.defaultCharset()), ApiException::class.java)
}
