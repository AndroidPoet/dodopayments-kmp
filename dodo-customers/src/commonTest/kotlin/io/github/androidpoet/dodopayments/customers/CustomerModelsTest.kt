package io.github.androidpoet.dodopayments.customers

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CustomerModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createCustomerRequest_serialization_includesNameAndEmail() {
        val request = CreateCustomerRequest(name = "Jane Doe", email = "jane@example.com")

        val encoded = json.encodeToString(CreateCustomerRequest.serializer(), request)

        assertTrue(encoded.contains("Jane Doe"))
        assertTrue(encoded.contains("jane@example.com"))
    }

    @Test
    fun test_createCustomerRequest_withPhone_includesPhoneNumber() {
        val request = CreateCustomerRequest(
            name = "Jane",
            email = "jane@example.com",
            phoneNumber = "+1234567890",
        )

        val encoded = json.encodeToString(CreateCustomerRequest.serializer(), request)

        assertTrue(encoded.contains("phone_number"))
        assertTrue(encoded.contains("+1234567890"))
    }

    @Test
    fun test_customer_deserialization_parsesAllRequiredFields() {
        val customerJson = """
            {
                "customer_id": "cust_abc",
                "business_id": "biz_xyz",
                "name": "Jane Doe",
                "email": "jane@example.com",
                "created_at": "2025-01-01T00:00:00Z"
            }
        """.trimIndent()

        val customer = json.decodeFromString<Customer>(customerJson)

        assertEquals("cust_abc", customer.customerId)
        assertEquals("Jane Doe", customer.name)
        assertEquals("jane@example.com", customer.email)
        assertNull(customer.phoneNumber)
    }

    @Test
    fun test_updateCustomerRequest_serialization_omitsNullFields() {
        val request = UpdateCustomerRequest(name = "Jane Smith")

        val encoded = json.encodeToString(UpdateCustomerRequest.serializer(), request)

        assertTrue(encoded.contains("Jane Smith"))
        assertTrue(!encoded.contains("email"))
        assertTrue(!encoded.contains("phone_number"))
    }

    @Test
    fun test_portalSessionResponse_deserialization_parsesLink() {
        val responseJson = """{"link":"https://portal.dodopayments.com/session_abc"}"""

        val response = json.decodeFromString<PortalSessionResponse>(responseJson)

        assertEquals("https://portal.dodopayments.com/session_abc", response.link)
    }
}
