package io.github.androidpoet.dodopayments.products

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductModelsTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    @Test
    fun test_createProductRequest_withOneTimePrice_serializes() {
        val request = CreateProductRequest(
            name = "E-book",
            taxCategory = TaxCategory.EBook,
            price = PriceInput.OneTime(currency = "USD", productPrice = 1999),
        )

        val encoded = json.encodeToString(CreateProductRequest.serializer(), request)

        assertTrue(encoded.contains("E-book"))
        assertTrue(encoded.contains("1999"))
        assertTrue(encoded.contains("USD"))
    }

    @Test
    fun test_createProductRequest_withRecurringPrice_serializes() {
        val request = CreateProductRequest(
            name = "Pro Monthly",
            taxCategory = TaxCategory.Saas,
            price = PriceInput.Recurring(
                currency = "USD",
                productPrice = 999,
                paymentFrequencyCount = 1,
                paymentFrequencyInterval = "month",
                subscriptionPeriodCount = 1,
                subscriptionPeriodInterval = "month",
            ),
        )

        val encoded = json.encodeToString(CreateProductRequest.serializer(), request)

        assertTrue(encoded.contains("Pro Monthly"))
        assertTrue(encoded.contains("month"))
        assertTrue(encoded.contains("999"))
    }

    @Test
    fun test_taxCategory_allValues_deserializeCorrectly() {
        val cases = mapOf(
            "digital_products" to TaxCategory.DigitalProducts,
            "saas" to TaxCategory.Saas,
            "e_book" to TaxCategory.EBook,
        )

        cases.forEach { (raw, expected) ->
            val decoded = json.decodeFromString<TaxCategory>(""""$raw"""")
            assertEquals(expected, decoded)
        }
    }

    @Test
    fun test_product_deserialization_parsesRequiredFields() {
        val productJson = """
            {
                "product_id": "prod_abc",
                "business_id": "biz_xyz",
                "name": "Pro Plan",
                "tax_category": "saas",
                "is_recurring": true,
                "created_at": "2025-01-01T00:00:00Z",
                "updated_at": "2025-01-01T00:00:00Z"
            }
        """.trimIndent()

        val product = json.decodeFromString<Product>(productJson)

        assertEquals("prod_abc", product.productId)
        assertEquals("Pro Plan", product.name)
        assertEquals(TaxCategory.Saas, product.taxCategory)
        assertTrue(product.isRecurring)
    }
}
