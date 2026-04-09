package io.github.androidpoet.dodopayments.payments

import io.github.androidpoet.dodopayments.core.BillingInput
import io.github.androidpoet.dodopayments.core.CustomerInput
import io.github.androidpoet.dodopayments.core.ProductCartItem
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PaymentModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createPaymentRequest_serialization_includesRequiredFields() {
        val request = CreatePaymentRequest(
            productCart = listOf(ProductCartItem("prod_123", 1)),
            customer = CustomerInput(email = "test@example.com", name = "Test User"),
            billing = BillingInput(country = "US"),
        )

        val encoded = json.encodeToString(CreatePaymentRequest.serializer(), request)

        assertTrue(encoded.contains("product_cart"))
        assertTrue(encoded.contains("prod_123"))
        assertTrue(encoded.contains("test@example.com"))
        assertTrue(encoded.contains("US"))
    }

    @Test
    fun test_createPaymentRequest_serialization_omitsNullOptionalFields() {
        val request = CreatePaymentRequest(
            productCart = listOf(ProductCartItem("prod_123", 1)),
            customer = CustomerInput(email = "test@example.com"),
            billing = BillingInput(country = "US"),
        )

        val encoded = json.encodeToString(CreatePaymentRequest.serializer(), request)

        assertTrue(!encoded.contains("discount_code"))
        assertTrue(!encoded.contains("tax_id"))
        assertTrue(!encoded.contains("return_url"))
    }

    @Test
    fun test_paymentResponse_deserialization_parsesAllFields() {
        val responseJson = """
            {
                "payment_id": "pay_abc",
                "business_id": "biz_xyz",
                "total_amount": 4900,
                "currency": "USD",
                "created_at": "2025-01-01T00:00:00Z",
                "status": "succeeded",
                "customer": {"customer_id": "cust_1", "email": "user@example.com"},
                "metadata": {}
            }
        """.trimIndent()

        val response = json.decodeFromString<PaymentResponse>(responseJson)

        assertEquals("pay_abc", response.paymentId)
        assertEquals(4900, response.totalAmount)
        assertEquals("USD", response.currency)
        assertEquals("succeeded", response.status)
        assertEquals("cust_1", response.customer.customerId)
    }

    @Test
    fun test_paymentResponse_deserialization_handlesOptionalNullFields() {
        val responseJson = """
            {
                "payment_id": "pay_abc",
                "business_id": "biz_xyz",
                "total_amount": 1000,
                "currency": "USD",
                "created_at": "2025-01-01T00:00:00Z",
                "status": "processing",
                "customer": {"customer_id": "cust_1"},
                "metadata": {}
            }
        """.trimIndent()

        val response = json.decodeFromString<PaymentResponse>(responseJson)

        assertNull(response.subscriptionId)
        assertNull(response.invoiceId)
        assertNull(response.refundStatus)
    }

    @Test
    fun test_listPaymentsParams_toQueryParams_omitsNullValues() {
        val params = ListPaymentsParams(pageSize = 10, status = "succeeded")

        assertEquals(10, params.pageSize)
        assertEquals("succeeded", params.status)
        assertNull(params.customerId)
    }
}
