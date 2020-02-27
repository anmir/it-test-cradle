package org.example.api.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val defaultMapper = jacksonObjectMapper()

inline fun <reified T> String.deserialize(objectMapper: ObjectMapper): T {
    if (T::class is Collection<*>) {
        val collection = objectMapper.typeFactory.constructCollectionType(List::class.java, T::class.java)
        return objectMapper.readValue(this, collection)
    }
    return objectMapper.readValue(this)
}

fun Any.toJson(objectMapper: ObjectMapper = defaultMapper): String {
    return objectMapper.writeValueAsString(this)
}