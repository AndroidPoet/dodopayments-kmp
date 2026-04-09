package io.github.androidpoet.dodopayments.payments

import io.github.androidpoet.dodopayments.core.ProductCartItem
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CheckoutModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createCheckoutRequest_serialization_includesProductCart() {
        val request = CreateCheckoutRequest(
            productCart = listOf(ProductCartItem("prod_1", 2)),
        )

        val encoded = json.encodeToString(CreateCheckoutRequest.serializer(), request)

        assertTrue(encoded.contains("product_cart"))
        assertTrue(encoded.contains("prod_1"))
    }

    @Test
    fun test_checkoutResponse_deserialization_parsesSessionIdAndUrl() {
        val responseJson = """{"session_id":"sess_abc","checkout_url":"https://checkout.dodopayments.com/sess_abc"}"""

        val response = json.decodeFromString<CheckoutResponse>(responseJson)

        assertEquals("sess_abc", response.sessionId)
        assertEquals("https://checkout.dodopayments.com/sess_abc", response.checkoutUrl)
    }

    @Test
    fun test_checkoutResponse_deserialization_nullUrlWhenAbsent() {
        val responseJson = """{"session_id":"sess_abc"}"""

        val response = json.decodeFromString<CheckoutResponse>(responseJson)

        assertEquals("sess_abc", response.sessionId)
        assertNull(response.checkoutUrl)
    }
}
