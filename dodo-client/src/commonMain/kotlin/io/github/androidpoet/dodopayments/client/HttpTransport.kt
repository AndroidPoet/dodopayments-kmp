package io.github.androidpoet.dodopayments.client

import io.github.androidpoet.dodopayments.core.DodoError
import io.github.androidpoet.dodopayments.core.DodoResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class HttpTransport(
    private val apiKey: String,
    private val baseUrl: String,
    config: DodoConfig,
) : DodoClient {

    override val apiKey: String get() = this.apiKey
    override val baseUrl: String get() = this.baseUrl

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    private val client = HttpClient(createPlatformEngine()) {
        install(ContentNegotiation) { json(json) }
        if (config.logging) {
            install(Logging) { level = config.logLevel }
        }
    }

    private fun authHeader() = "Bearer $apiKey"

    override suspend fun get(path: String, params: Map<String, String?>): DodoResult<String> =
        runCatching {
            client.get("$baseUrl$path") {
                header("Authorization", authHeader())
                params.forEach { (k, v) -> if (v != null) url.parameters.append(k, v) }
            }
        }.toResult()

    override suspend fun post(path: String, body: String): DodoResult<String> =
        runCatching {
            client.post("$baseUrl$path") {
                header("Authorization", authHeader())
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }.toResult()

    override suspend fun patch(path: String, body: String): DodoResult<String> =
        runCatching {
            client.patch("$baseUrl$path") {
                header("Authorization", authHeader())
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }.toResult()

    override suspend fun put(path: String, body: String): DodoResult<String> =
        runCatching {
            client.put("$baseUrl$path") {
                header("Authorization", authHeader())
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }.toResult()

    override suspend fun delete(path: String): DodoResult<String> =
        runCatching {
            client.delete("$baseUrl$path") {
                header("Authorization", authHeader())
            }
        }.toResult()

    override fun close() = client.close()

    private suspend fun Result<io.ktor.client.statement.HttpResponse>.toResult(): DodoResult<String> {
        val response = getOrElse { e ->
            return DodoResult.Failure(DodoError(message = e.message ?: "Network error"))
        }
        val body = response.bodyAsText()
        return if (response.status.isSuccess()) {
            DodoResult.Success(body)
        } else {
            val error = try {
                json.decodeFromString<DodoError>(body)
            } catch (_: Exception) {
                DodoError(message = body, code = response.status.value.toString())
            }
            DodoResult.Failure(error)
        }
    }
}
