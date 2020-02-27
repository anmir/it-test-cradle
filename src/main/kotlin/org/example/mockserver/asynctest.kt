package org.example.mockserver

import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

// для смоук тестов, при вызове большого количества методов должен быть профит
fun main() = runBlocking {
    val apiCalls = mutableListOf<Deferred<Unit>>()

    apiCalls += async {
        "http://localhost:8080/three".httpGet().awaitStringResponseResult().third.fold(
            { data -> println("Api::three:: Passed: ${data == "good"} :: actual::$data") },
            { err -> println("TestError: $err") })
    }

    apiCalls += async {
        "http://localhost:8080/one".httpGet().awaitStringResponseResult().third.fold(
            { data -> println("Api::one:: Passed: ${data == "good"} :: actual::$data") },
            { err -> println("TestError: $err") })
    }

    apiCalls += async {
        "http://localhost:8080/two".httpGet().awaitStringResponseResult().third.fold(
            { data -> println("Api::two:: Passed: ${data == "good"} :: actual::$data") },
            { err -> println("TestError: $err") })
    }

    apiCalls.awaitAll()
    println("done")
}
