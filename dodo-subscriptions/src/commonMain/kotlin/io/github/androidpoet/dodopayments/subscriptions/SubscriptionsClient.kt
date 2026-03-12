package io.github.androidpoet.dodopayments.subscriptions

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import io.github.androidpoet.dodopayments.core.PagedList
import kotlinx.serialization.json.Json

public class SubscriptionsClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createSubscription(request: CreateSubscriptionRequest): DodoResult<CreateSubscriptionResponse> =
        client.post("/subscriptions", json.encodeToString(CreateSubscriptionRequest.serializer(), request))
            .map { json.decodeFromString<CreateSubscriptionResponse>(it) }

    public suspend fun getSubscription(subscriptionId: String): DodoResult<SubscriptionResponse> =
        client.get("/subscriptions/$subscriptionId")
            .map { json.decodeFromString<SubscriptionResponse>(it) }

    public suspend fun listSubscriptions(params: ListSubscriptionsParams = ListSubscriptionsParams()): DodoResult<PagedList<SubscriptionResponse>> =
        client.get("/subscriptions", params.toQueryParams())
            .map { json.decodeFromString<PagedList<SubscriptionResponse>>(it) }

    public suspend fun updateSubscription(subscriptionId: String, request: UpdateSubscriptionRequest): DodoResult<SubscriptionResponse> =
        client.patch("/subscriptions/$subscriptionId", json.encodeToString(UpdateSubscriptionRequest.serializer(), request))
            .map { json.decodeFromString<SubscriptionResponse>(it) }

    public suspend fun changePlan(subscriptionId: String, request: ChangePlanRequest): DodoResult<SubscriptionResponse> =
        client.post("/subscriptions/$subscriptionId/change-plan", json.encodeToString(ChangePlanRequest.serializer(), request))
            .map { json.decodeFromString<SubscriptionResponse>(it) }

    public suspend fun chargeOnDemand(subscriptionId: String, request: OnDemandChargeRequest): DodoResult<OnDemandChargeResponse> =
        client.post("/subscriptions/$subscriptionId/charge", json.encodeToString(OnDemandChargeRequest.serializer(), request))
            .map { json.decodeFromString<OnDemandChargeResponse>(it) }

    public suspend fun cancelSubscription(subscriptionId: String): DodoResult<SubscriptionResponse> =
        updateSubscription(subscriptionId, UpdateSubscriptionRequest(cancelAtNextBillingDate = true))

    private fun ListSubscriptionsParams.toQueryParams(): Map<String, String?> = mapOf(
        "page_size" to pageSize?.toString(),
        "page_number" to pageNumber?.toString(),
        "customer_id" to customerId,
        "status" to status,
        "product_id" to productId,
        "created_at_gte" to createdAtGte,
        "created_at_lte" to createdAtLte,
    )
}
