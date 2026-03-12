package io.github.androidpoet.dodopayments.client

import io.github.androidpoet.dodopayments.core.DodoResult

public interface DodoClient {
    public val apiKey: String
    public val baseUrl: String

    public suspend fun get(path: String, params: Map<String, String?> = emptyMap()): DodoResult<String>
    public suspend fun post(path: String, body: String): DodoResult<String>
    public suspend fun patch(path: String, body: String): DodoResult<String>
    public suspend fun put(path: String, body: String): DodoResult<String>
    public suspend fun delete(path: String): DodoResult<String>

    public fun close()
}
