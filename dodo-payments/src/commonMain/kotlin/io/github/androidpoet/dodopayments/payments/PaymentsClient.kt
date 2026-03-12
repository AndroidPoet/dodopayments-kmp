package io.github.androidpoet.dodopayments.payments

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import io.github.androidpoet.dodopayments.core.PagedList
import kotlinx.serialization.json.Json

public class PaymentsClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createPayment(request: CreatePaymentRequest): DodoResult<CreatePaymentResponse> =
        client.post("/payments", json.encodeToString(CreatePaymentRequest.serializer(), request))
            .map { json.decodeFromString<CreatePaymentResponse>(it) }

    public suspend fun getPayment(paymentId: String): DodoResult<PaymentResponse> =
        client.get("/payments/$paymentId")
            .map { json.decodeFromString<PaymentResponse>(it) }

    public suspend fun listPayments(params: ListPaymentsParams = ListPaymentsParams()): DodoResult<PagedList<PaymentResponse>> =
        client.get("/payments", params.toQueryParams())
            .map { json.decodeFromString<PagedList<PaymentResponse>>(it) }

    private fun ListPaymentsParams.toQueryParams(): Map<String, String?> = mapOf(
        "page_size" to pageSize?.toString(),
        "page_number" to pageNumber?.toString(),
        "customer_id" to customerId,
        "subscription_id" to subscriptionId,
        "status" to status,
        "created_at_gte" to createdAtGte,
        "created_at_lte" to createdAtLte,
    )
}
