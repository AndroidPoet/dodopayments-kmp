package io.github.androidpoet.dodopayments.subscriptions

import io.github.androidpoet.dodopayments.core.BillingInput
import io.github.androidpoet.dodopayments.core.CustomerInput
import io.github.androidpoet.dodopayments.core.CustomerSummary
import io.github.androidpoet.dodopayments.core.ProductCartItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class SubscriptionStatus {
    @SerialName("pending") Pending,
    @SerialName("active") Active,
    @SerialName("on_hold") OnHold,
    @SerialName("cancelled") Cancelled,
    @SerialName("failed") Failed,
    @SerialName("expired") Expired,
}

@Serializable
public data class AddonItem(
    @SerialName("addon_id") val addonId: String,
    val quantity: Int,
)

@Serializable
public data class CreateSubscriptionRequest(
    @SerialName("product_id") val productId: String,
    val quantity: Int,
    val customer: CustomerInput,
    val billing: BillingInput,
    val addons: List<AddonItem>? = null,
    @SerialName("billing_currency") val billingCurrency: String? = null,
    @SerialName("discount_code") val discountCode: String? = null,
    @SerialName("force_3ds") val force3ds: Boolean? = null,
    val metadata: Map<String, String>? = null,
    @SerialName("one_time_product_cart") val oneTimeProductCart: List<ProductCartItem>? = null,
    @SerialName("payment_link") val paymentLink: Boolean? = null,
    @SerialName("return_url") val returnUrl: String? = null,
    @SerialName("short_link") val shortLink: Boolean? = null,
    @SerialName("show_saved_payment_methods") val showSavedPaymentMethods: Boolean? = null,
    @SerialName("tax_id") val taxId: String? = null,
    @SerialName("trial_period_days") val trialPeriodDays: Int? = null,
)

@Serializable
public data class CreateSubscriptionResponse(
    @SerialName("subscription_id") val subscriptionId: String,
    @SerialName("payment_id") val paymentId: String? = null,
    @SerialName("recurring_pre_tax_amount") val recurringPreTaxAmount: Int,
    val customer: CustomerSummary,
    val metadata: Map<String, String> = emptyMap(),
    val addons: List<AddonItem>? = null,
    @SerialName("payment_link") val paymentLink: String? = null,
    @SerialName("client_secret") val clientSecret: String? = null,
    @SerialName("discount_id") val discountId: String? = null,
)

@Serializable
public data class SubscriptionResponse(
    @SerialName("subscription_id") val subscriptionId: String,
    @SerialName("product_id") val productId: String,
    val customer: CustomerSummary,
    val status: SubscriptionStatus,
    @SerialName("recurring_pre_tax_amount") val recurringPreTaxAmount: Int,
    @SerialName("tax_inclusive") val taxInclusive: Boolean,
    val currency: String,
    val quantity: Int,
    @SerialName("trial_period_days") val trialPeriodDays: Int? = null,
    @SerialName("subscription_period_interval") val subscriptionPeriodInterval: String? = null,
    @SerialName("subscription_period_count") val subscriptionPeriodCount: Int? = null,
    @SerialName("payment_frequency_interval") val paymentFrequencyInterval: String? = null,
    @SerialName("payment_frequency_count") val paymentFrequencyCount: Int? = null,
    @SerialName("next_billing_date") val nextBillingDate: String? = null,
    @SerialName("previous_billing_date") val previousBillingDate: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("cancelled_at") val cancelledAt: String? = null,
    val metadata: Map<String, String> = emptyMap(),
    @SerialName("cancel_at_next_billing_date") val cancelAtNextBillingDate: Boolean? = null,
)

@Serializable
public data class UpdateSubscriptionRequest(
    @SerialName("cancel_at_next_billing_date") val cancelAtNextBillingDate: Boolean? = null,
    @SerialName("customer_name") val customerName: String? = null,
    val metadata: Map<String, String>? = null,
    @SerialName("next_billing_date") val nextBillingDate: String? = null,
    val status: SubscriptionStatus? = null,
    @SerialName("tax_id") val taxId: String? = null,
)

@Serializable
public data class ChangePlanRequest(
    @SerialName("product_id") val productId: String,
    val quantity: Int,
    @SerialName("proration_billing_mode") val prorationBillingMode: String,
    @SerialName("discount_code") val discountCode: String? = null,
    @SerialName("on_payment_failure") val onPaymentFailure: String? = null,
    val addons: List<AddonItem>? = null,
    val metadata: Map<String, String>? = null,
)

@Serializable
public data class OnDemandChargeRequest(
    @SerialName("product_price") val productPrice: Int,
    @SerialName("product_currency") val productCurrency: String? = null,
    @SerialName("product_description") val productDescription: String? = null,
    val metadata: Map<String, String>? = null,
)

@Serializable
public data class OnDemandChargeResponse(
    @SerialName("payment_id") val paymentId: String,
)

@Serializable
public data class ListSubscriptionsParams(
    @SerialName("page_size") val pageSize: Int? = null,
    @SerialName("page_number") val pageNumber: Int? = null,
    @SerialName("customer_id") val customerId: String? = null,
    val status: String? = null,
    @SerialName("product_id") val productId: String? = null,
    @SerialName("created_at_gte") val createdAtGte: String? = null,
    @SerialName("created_at_lte") val createdAtLte: String? = null,
)
