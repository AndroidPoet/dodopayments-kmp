package io.github.androidpoet.dodopayments.billing

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import io.github.androidpoet.dodopayments.core.PagedList
import kotlinx.serialization.json.Json

public class RefundsClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createRefund(request: CreateRefundRequest): DodoResult<Refund> =
        client.post("/refunds", json.encodeToString(CreateRefundRequest.serializer(), request))
            .map { json.decodeFromString<Refund>(it) }

    public suspend fun getRefund(refundId: String): DodoResult<Refund> =
        client.get("/refunds/$refundId")
            .map { json.decodeFromString<Refund>(it) }

    public suspend fun listRefunds(params: ListRefundsParams = ListRefundsParams()): DodoResult<PagedList<Refund>> =
        client.get("/refunds", params.toQueryParams())
            .map { json.decodeFromString<PagedList<Refund>>(it) }

    private fun ListRefundsParams.toQueryParams(): Map<String, String?> = mapOf(
        "page_size" to pageSize?.toString(),
        "page_number" to pageNumber?.toString(),
        "status" to status,
        "customer_id" to customerId,
        "subscription_id" to subscriptionId,
        "created_at_gte" to createdAtGte,
        "created_at_lte" to createdAtLte,
    )
}
