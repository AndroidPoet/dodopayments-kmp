package io.github.androidpoet.dodopayments.subscriptions

import io.github.androidpoet.dodopayments.core.BillingInput
import io.github.androidpoet.dodopayments.core.CustomerInput
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SubscriptionModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createSubscriptionRequest_serialization_includesRequiredFields() {
        val request = CreateSubscriptionRequest(
            productId = "prod_123",
            quantity = 1,
            customer = CustomerInput(email = "test@example.com", name = "Test User"),
            billing = BillingInput(country = "US"),
        )

        val encoded = json.encodeToString(CreateSubscriptionRequest.serializer(), request)

        assertTrue(encoded.contains("prod_123"))
        assertTrue(encoded.contains("test@example.com"))
    }

    @Test
    fun test_createSubscriptionRequest_withTrial_includesTrialPeriodDays() {
        val request = CreateSubscriptionRequest(
            productId = "prod_123",
            quantity = 1,
            customer = CustomerInput(email = "test@example.com"),
            billing = BillingInput(country = "US"),
            trialPeriodDays = 14,
        )

        val encoded = json.encodeToString(CreateSubscriptionRequest.serializer(), request)

        assertTrue(encoded.contains("trial_period_days"))
        assertTrue(encoded.contains("14"))
    }

    @Test
    fun test_subscriptionResponse_deserialization_parsesStatus() {
        val responseJson = """
            {
                "subscription_id": "sub_abc",
                "product_id": "prod_123",
                "customer": {"customer_id": "cust_1"},
                "status": "active",
                "recurring_pre_tax_amount": 999,
                "tax_inclusive": true,
                "currency": "USD",
                "quantity": 1,
                "created_at": "2025-01-01T00:00:00Z"
            }
        """.trimIndent()

        val response = json.decodeFromString<SubscriptionResponse>(responseJson)

        assertEquals("sub_abc", response.subscriptionId)
        assertEquals(SubscriptionStatus.Active, response.status)
        assertEquals(999, response.recurringPreTaxAmount)
    }

    @Test
    fun test_subscriptionStatus_allValues_deserializeCorrectly() {
        val statuses = listOf("pending", "active", "on_hold", "cancelled", "failed", "expired")
        val expected = listOf(
            SubscriptionStatus.Pending,
            SubscriptionStatus.Active,
            SubscriptionStatus.OnHold,
            SubscriptionStatus.Cancelled,
            SubscriptionStatus.Failed,
            SubscriptionStatus.Expired,
        )

        statuses.zip(expected).forEach { (raw, expectedStatus) ->
            val decoded = json.decodeFromString<SubscriptionStatus>(""""$raw"""")
            assertEquals(expectedStatus, decoded)
        }
    }

    @Test
    fun test_changePlanRequest_serialization_includesProrationMode() {
        val request = ChangePlanRequest(
            productId = "prod_new",
            quantity = 2,
            prorationBillingMode = "prorated_immediately",
        )

        val encoded = json.encodeToString(ChangePlanRequest.serializer(), request)

        assertTrue(encoded.contains("prorated_immediately"))
        assertTrue(encoded.contains("prod_new"))
    }

    @Test
    fun test_updateSubscriptionRequest_cancelAtNextBilling_serializes() {
        val request = UpdateSubscriptionRequest(cancelAtNextBillingDate = true)

        val encoded = json.encodeToString(UpdateSubscriptionRequest.serializer(), request)

        assertTrue(encoded.contains("cancel_at_next_billing_date"))
        assertTrue(encoded.contains("true"))
    }
}
