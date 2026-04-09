package io.github.androidpoet.dodopayments.billing

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RefundModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createRefundRequest_withPaymentIdOnly_serializes() {
        val request = CreateRefundRequest(paymentId = "pay_abc")

        val encoded = json.encodeToString(CreateRefundRequest.serializer(), request)

        assertTrue(encoded.contains("pay_abc"))
        assertTrue(!encoded.contains("reason"))
    }

    @Test
    fun test_createRefundRequest_withPartialItems_serializes() {
        val request = CreateRefundRequest(
            paymentId = "pay_abc",
            items = listOf(PartialRefundItem(itemId = "item_1", amount = 500)),
            reason = "Customer request",
        )

        val encoded = json.encodeToString(CreateRefundRequest.serializer(), request)

        assertTrue(encoded.contains("item_1"))
        assertTrue(encoded.contains("Customer request"))
    }

    @Test
    fun test_refund_deserialization_parsesStatus() {
        val refundJson = """
            {
                "refund_id": "ref_abc",
                "payment_id": "pay_abc",
                "business_id": "biz_xyz",
                "status": "succeeded",
                "created_at": "2025-01-01T00:00:00Z",
                "is_partial": false,
                "amount": 4900,
                "currency": "USD"
            }
        """.trimIndent()

        val refund = json.decodeFromString<Refund>(refundJson)

        assertEquals("ref_abc", refund.refundId)
        assertEquals(RefundStatus.Succeeded, refund.status)
        assertEquals(4900, refund.amount)
        assertEquals(false, refund.isPartial)
    }

    @Test
    fun test_refundStatus_allValues_deserializeCorrectly() {
        val cases = mapOf(
            "succeeded" to RefundStatus.Succeeded,
            "failed" to RefundStatus.Failed,
            "pending" to RefundStatus.Pending,
            "review" to RefundStatus.Review,
        )

        cases.forEach { (raw, expected) ->
            assertEquals(expected, json.decodeFromString<RefundStatus>(""""$raw""""))
        }
    }
}
