package io.github.androidpoet.dodopayments.billing

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DiscountModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createDiscountRequest_withCode_serializes() {
        val request = CreateDiscountRequest(amount = 2000, code = "SAVE20")

        val encoded = json.encodeToString(CreateDiscountRequest.serializer(), request)

        assertTrue(encoded.contains("SAVE20"))
        assertTrue(encoded.contains("2000"))
    }

    @Test
    fun test_createDiscountRequest_withUsageLimit_serializes() {
        val request = CreateDiscountRequest(
            amount = 1000,
            usageLimit = 50,
            restrictedTo = listOf("prod_abc"),
        )

        val encoded = json.encodeToString(CreateDiscountRequest.serializer(), request)

        assertTrue(encoded.contains("50"))
        assertTrue(encoded.contains("prod_abc"))
    }

    @Test
    fun test_discount_deserialization_parsesRequiredFields() {
        val discountJson = """
            {
                "discount_id": "disc_abc",
                "business_id": "biz_xyz",
                "type": "percentage",
                "amount": 2000,
                "times_used": 5,
                "created_at": "2025-01-01T00:00:00Z",
                "preserve_on_plan_change": false
            }
        """.trimIndent()

        val discount = json.decodeFromString<Discount>(discountJson)

        assertEquals("disc_abc", discount.discountId)
        assertEquals(2000, discount.amount)
        assertEquals(5, discount.timesUsed)
        assertNull(discount.code)
        assertNull(discount.usageLimit)
    }

    @Test
    fun test_validateDiscountResponse_valid_deserializes() {
        val responseJson = """{"valid":true}"""

        val response = json.decodeFromString<ValidateDiscountResponse>(responseJson)

        assertEquals(true, response.valid)
        assertNull(response.discount)
    }
}
