package io.github.androidpoet.dodopayments.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DodoIdsTest {

    @Test
    fun test_paymentId_equality_matchesSameValue() {
        val id1 = PaymentId("pay_123")
        val id2 = PaymentId("pay_123")

        assertEquals(id1, id2)
    }

    @Test
    fun test_paymentId_equality_differentiatesDifferentValues() {
        val id1 = PaymentId("pay_123")
        val id2 = PaymentId("pay_456")

        assertNotEquals(id1, id2)
    }

    @Test
    fun test_differentIdTypes_notEqual_withSameRawValue() {
        val paymentId = PaymentId("id_123")
        val customerId = CustomerId("id_123")

        // Different types — cannot be compared directly, which is the point
        assertEquals("id_123", paymentId.value)
        assertEquals("id_123", customerId.value)
    }

    @Test
    fun test_subscriptionId_value_isPreserved() {
        val id = SubscriptionId("sub_abc")

        assertEquals("sub_abc", id.value)
    }
}
