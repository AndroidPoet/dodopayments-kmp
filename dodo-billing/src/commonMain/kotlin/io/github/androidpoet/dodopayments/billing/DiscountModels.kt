package io.github.androidpoet.dodopayments.billing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateDiscountRequest(
    val type: String = "percentage",
    val amount: Int,
    val code: String? = null,
    val name: String? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("restricted_to") val restrictedTo: List<String>? = null,
    @SerialName("usage_limit") val usageLimit: Int? = null,
    @SerialName("subscription_cycles") val subscriptionCycles: Int? = null,
    @SerialName("preserve_on_plan_change") val preserveOnPlanChange: Boolean? = null,
)

@Serializable
public data class Discount(
    @SerialName("discount_id") val discountId: String,
    @SerialName("business_id") val businessId: String,
    val type: String,
    val code: String? = null,
    val amount: Int,
    @SerialName("times_used") val timesUsed: Int,
    @SerialName("restricted_to") val restrictedTo: List<String>? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("preserve_on_plan_change") val preserveOnPlanChange: Boolean? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
    val name: String? = null,
    @SerialName("subscription_cycles") val subscriptionCycles: Int? = null,
    @SerialName("usage_limit") val usageLimit: Int? = null,
)

@Serializable
public data class ValidateDiscountRequest(
    val code: String,
    @SerialName("product_ids") val productIds: List<String>? = null,
)

@Serializable
public data class ValidateDiscountResponse(
    val valid: Boolean,
    val discount: Discount? = null,
)
