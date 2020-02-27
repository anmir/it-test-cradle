package org.example.api.authors

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import org.example.api.common.ApiException
import org.example.api.common.ApiResult
import org.example.api.common.toApiResponseSuspend


class AuthorsImpl(
    private val client: HttpClient
) : AuthorsApi {

    override fun getAllAuthors(): ApiResult<List<Author>, ApiException> {
        return runBlocking {
            val t: ApiResult<List<Author>, ApiException> = client.get<HttpResponse> {
                url("http://localhost:8080/authors")
            }.toApiResponseSuspend()
            t
        }
    }

    override fun getAuthor(id: String): ApiResult<Author, ApiException> {
        val author = runBlocking {
            client.get<Author> {
                url("http://localhost:8080/authors/$id")
            }
        }
        return ApiResult.success(author)
    }

}


fun defaultKtorClient(): HttpClient {
    return HttpClient(OkHttp) {
        defaultRequest {
            //basePath...
        }
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
        useDefaultTransformers = true
        expectSuccess = false
        //addresponseValidators
    }
}