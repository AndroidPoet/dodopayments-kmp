package io.github.androidpoet.dodopayments.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DodoErrorTest {

    @Test
    fun test_dodoError_toException_returnsExceptionWithMessage() {
        val error = DodoError(message = "payment failed", code = "PAYMENT_FAILED")

        val exception = error.toException()

        assertNotNull(exception)
        assertEquals("payment failed", exception.message)
        assertEquals(error, exception.error)
    }

    @Test
    fun test_dodoError_withAllFields_preservesAllValues() {
        val error = DodoError(
            message = "not found",
            code = "404",
            errorType = "resource_missing",
            details = "Payment pay_123 does not exist",
        )

        assertEquals("not found", error.message)
        assertEquals("404", error.code)
        assertEquals("resource_missing", error.errorType)
        assertEquals("Payment pay_123 does not exist", error.details)
    }
}
