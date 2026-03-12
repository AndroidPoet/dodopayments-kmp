package io.github.androidpoet.dodopayments.payments

import io.github.androidpoet.dodopayments.core.CustomerInput
import io.github.androidpoet.dodopayments.core.CustomerSummary
import io.github.androidpoet.dodopayments.core.ProductCartItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateCheckoutRequest(
    @SerialName("product_cart") val productCart: List<ProductCartItem>,
    val customer: CustomerInput? = null,
    @SerialName("billing_currency") val billingCurrency: String? = null,
    @SerialName("discount_code") val discountCode: String? = null,
    val metadata: Map<String, String>? = null,
    @SerialName("return_url") val returnUrl: String? = null,
    @SerialName("payment_link") val paymentLink: Boolean? = null,
    @SerialName("short_link") val shortLink: Boolean? = null,
)

@Serializable
public data class CheckoutResponse(
    @SerialName("session_id") val sessionId: String,
    @SerialName("checkout_url") val checkoutUrl: String? = null,
)

@Serializable
public data class CheckoutDetails(
    @SerialName("session_id") val sessionId: String,
    val status: String,
    @SerialName("product_cart") val productCart: List<ProductCartItem>? = null,
    val customer: CustomerSummary? = null,
    val metadata: Map<String, String> = emptyMap(),
)
