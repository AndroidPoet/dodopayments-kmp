package io.github.androidpoet.dodopayments.customers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class CreateCustomerRequest(
    val name: String,
    val email: String,
    @SerialName("phone_number") val phoneNumber: String? = null,
    val metadata: Map<String, String>? = null,
)

@Serializable
public data class Customer(
    @SerialName("customer_id") val customerId: String,
    @SerialName("business_id") val businessId: String,
    val name: String,
    val email: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("phone_number") val phoneNumber: String? = null,
    val metadata: Map<String, String>? = null,
)

@Serializable
public data class UpdateCustomerRequest(
    val name: String? = null,
    val email: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    val metadata: Map<String, String>? = null,
)

@Serializable
public data class PortalSessionResponse(
    val link: String,
)

@Serializable
public data class ListCustomersParams(
    @SerialName("page_size") val pageSize: Int? = null,
    @SerialName("page_number") val pageNumber: Int? = null,
    val email: String? = null,
    val name: String? = null,
    @SerialName("created_at_gte") val createdAtGte: String? = null,
    @SerialName("created_at_lte") val createdAtLte: String? = null,
)
