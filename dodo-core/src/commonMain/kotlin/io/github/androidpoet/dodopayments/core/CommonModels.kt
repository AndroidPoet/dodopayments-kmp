package io.github.androidpoet.dodopayments.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CustomerInput(
    @SerialName("customer_id") val customerId: String? = null,
    val email: String? = null,
    val name: String? = null,
)

@Serializable
public data class BillingInput(
    val country: String,
    val city: String? = null,
    val state: String? = null,
    val street: String? = null,
    val zipcode: String? = null,
)

@Serializable
public data class CustomerSummary(
    @SerialName("customer_id") val customerId: String,
    val name: String? = null,
    val email: String? = null,
)

@Serializable
public data class ProductCartItem(
    @SerialName("product_id") val productId: String,
    val quantity: Int,
)
