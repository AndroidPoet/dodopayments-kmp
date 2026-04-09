package io.github.androidpoet.dodopayments.billing

import io.github.androidpoet.dodopayments.core.CustomerSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PartialRefundItem(
    @SerialName("item_id") val itemId: String,
    val amount: Int? = null,
    @SerialName("tax_inclusive") val taxInclusive: Boolean = true,
)

@Serializable
public data class CreateRefundRequest(
    @SerialName("payment_id") val paymentId: String,
    val items: List<PartialRefundItem>? = null,
    val metadata: Map<String, String>? = null,
    val reason: String? = null,
)

@Serializable
public enum class RefundStatus {
    @SerialName("succeeded") Succeeded,
    @SerialName("failed") Failed,
    @SerialName("pending") Pending,
    @SerialName("review") Review,
}

@Serializable
public data class Refund(
    @SerialName("refund_id") val refundId: String,
    @SerialName("payment_id") val paymentId: String,
    @SerialName("business_id") val businessId: String,
    val status: RefundStatus,
    @SerialName("created_at") val createdAt: String,
    @SerialName("is_partial") val isPartial: Boolean,
    val amount: Int,
    val currency: String,
    val customer: CustomerSummary? = null,
    val metadata: Map<String, String>? = null,
    val reason: String? = null,
)

@Serializable
public data class ListRefundsParams(
    @SerialName("page_size") val pageSize: Int? = null,
    @SerialName("page_number") val pageNumber: Int? = null,
    val status: String? = null,
    @SerialName("customer_id") val customerId: String? = null,
    @SerialName("subscription_id") val subscriptionId: String? = null,
    @SerialName("created_at_gte") val createdAtGte: String? = null,
    @SerialName("created_at_lte") val createdAtLte: String? = null,
)
