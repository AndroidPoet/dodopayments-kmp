package io.github.androidpoet.dodopayments.customers

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import io.github.androidpoet.dodopayments.core.PagedList
import kotlinx.serialization.json.Json

public class CustomersClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createCustomer(request: CreateCustomerRequest): DodoResult<Customer> =
        client.post("/customers", json.encodeToString(CreateCustomerRequest.serializer(), request))
            .map { json.decodeFromString<Customer>(it) }

    public suspend fun getCustomer(customerId: String): DodoResult<Customer> =
        client.get("/customers/$customerId")
            .map { json.decodeFromString<Customer>(it) }

    public suspend fun listCustomers(params: ListCustomersParams = ListCustomersParams()): DodoResult<PagedList<Customer>> =
        client.get("/customers", params.toQueryParams())
            .map { json.decodeFromString<PagedList<Customer>>(it) }

    public suspend fun updateCustomer(customerId: String, request: UpdateCustomerRequest): DodoResult<Customer> =
        client.patch("/customers/$customerId", json.encodeToString(UpdateCustomerRequest.serializer(), request))
            .map { json.decodeFromString<Customer>(it) }

    public suspend fun createPortalSession(customerId: String, sendEmail: Boolean? = null): DodoResult<PortalSessionResponse> {
        val params = if (sendEmail != null) mapOf("send_email" to sendEmail.toString()) else emptyMap()
        return client.post("/customers/$customerId/customer-portal/session", "{}")
            .map { json.decodeFromString<PortalSessionResponse>(it) }
    }

    private fun ListCustomersParams.toQueryParams(): Map<String, String?> = mapOf(
        "page_size" to pageSize?.toString(),
        "page_number" to pageNumber?.toString(),
        "email" to email,
        "name" to name,
        "created_at_gte" to createdAtGte,
        "created_at_lte" to createdAtLte,
    )
}
