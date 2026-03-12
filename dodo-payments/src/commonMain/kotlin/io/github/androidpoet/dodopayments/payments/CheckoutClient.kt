package io.github.androidpoet.dodopayments.payments

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import kotlinx.serialization.json.Json

public class CheckoutClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createCheckout(request: CreateCheckoutRequest): DodoResult<CheckoutResponse> =
        client.post("/checkouts", json.encodeToString(CreateCheckoutRequest.serializer(), request))
            .map { json.decodeFromString<CheckoutResponse>(it) }

    public suspend fun getCheckout(sessionId: String): DodoResult<CheckoutDetails> =
        client.get("/checkouts/$sessionId")
            .map { json.decodeFromString<CheckoutDetails>(it) }
}
