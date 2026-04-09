package io.github.androidpoet.dodopayments.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CommonModelsTest {

    @Test
    fun test_customerInput_withIdOnly_hasNullEmailAndName() {
        val input = CustomerInput(customerId = "cust_123")

        assertEquals("cust_123", input.customerId)
        assertNull(input.email)
        assertNull(input.name)
    }

    @Test
    fun test_billingInput_requiresCountry_allowsOptionalFields() {
        val billing = BillingInput(country = "US", city = "New York", zipcode = "10001")

        assertEquals("US", billing.country)
        assertEquals("New York", billing.city)
        assertEquals("10001", billing.zipcode)
        assertNull(billing.state)
        assertNull(billing.street)
    }

    @Test
    fun test_productCartItem_preservesProductIdAndQuantity() {
        val item = ProductCartItem(productId = "prod_abc", quantity = 3)

        assertEquals("prod_abc", item.productId)
        assertEquals(3, item.quantity)
    }

    @Test
    fun test_customerSummary_optionalFieldsDefaultToNull() {
        val summary = CustomerSummary(customerId = "cust_1")

        assertEquals("cust_1", summary.customerId)
        assertNull(summary.name)
        assertNull(summary.email)
    }
}
