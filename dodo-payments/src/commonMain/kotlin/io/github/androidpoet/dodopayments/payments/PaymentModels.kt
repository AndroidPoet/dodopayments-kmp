package io.github.androidpoet.dodopayments.payments

import io.github.androidpoet.dodopayments.core.BillingInput
import io.github.androidpoet.dodopayments.core.CustomerInput
import io.github.androidpoet.dodopayments.core.CustomerSummary
import io.github.androidpoet.dodopayments.core.ProductCartItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreatePaymentRequest(
    @SerialName("product_cart") val productCart: List<ProductCartItem>,
    val customer: CustomerInput,
    val billing: BillingInput,
    @SerialName("allowed_payment_method_types") val allowedPaymentMethodTypes: List<String>? = null,
    @SerialName("billing_currency") val billingCurrency: String? = null,
    @SerialName("discount_code") val discountCode: String? = null,
    @SerialName("force_3ds") val force3ds: Boolean? = null,
    val metadata: Map<String, String>? = null,
    @SerialName("payment_link") val paymentLink: Boolean? = null,
    @SerialName("return_url") val returnUrl: String? = null,
    @SerialName("short_link") val shortLink: Boolean? = null,
    @SerialName("show_saved_payment_methods") val showSavedPaymentMethods: Boolean? = null,
    @SerialName("tax_id") val taxId: String? = null,
)

@Serializable
public data class CreatePaymentResponse(
    @SerialName("payment_id") val paymentId: String,
    @SerialName("total_amount") val totalAmount: Int,
    @SerialName("client_secret") val clientSecret: String,
    val customer: CustomerSummary,
    val metadata: Map<String, String> = emptyMap(),
    @SerialName("discount_id") val discountId: String? = null,
    @SerialName("expires_on") val expiresOn: String? = null,
    @SerialName("payment_link") val paymentLink: String? = null,
    @SerialName("product_cart") val productCart: List<ProductCartItem>? = null,
)

@Serializable
public data class PaymentResponse(
    @SerialName("payment_id") val paymentId: String,
    @SerialName("business_id") val businessId: String,
    @SerialName("total_amount") val totalAmount: Int,
    val currency: String,
    @SerialName("created_at") val createdAt: String,
    val status: String,
    val customer: CustomerSummary,
    val metadata: Map<String, String> = emptyMap(),
    @SerialName("product_cart") val productCart: List<ProductCartItem>? = null,
    @SerialName("payment_method") val paymentMethod: String? = null,
    @SerialName("payment_method_type") val paymentMethodType: String? = null,
    @SerialName("invoice_id") val invoiceId: String? = null,
    @SerialName("invoice_url") val invoiceUrl: String? = null,
    @SerialName("subscription_id") val subscriptionId: String? = null,
    @SerialName("refund_status") val refundStatus: String? = null,
    @SerialName("dispute_status") val disputeStatus: String? = null,
)

@Serializable
public data class ListPaymentsParams(
    @SerialName("page_size") val pageSize: Int? = null,
    @SerialName("page_number") val pageNumber: Int? = null,
    @SerialName("customer_id") val customerId: String? = null,
    @SerialName("subscription_id") val subscriptionId: String? = null,
    val status: String? = null,
    @SerialName("created_at_gte") val createdAtGte: String? = null,
    @SerialName("created_at_lte") val createdAtLte: String? = null,
)
