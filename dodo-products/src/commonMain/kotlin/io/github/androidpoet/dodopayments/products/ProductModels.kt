package io.github.androidpoet.dodopayments.products

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public enum class TaxCategory {
    @SerialName("digital_products") DigitalProducts,
    @SerialName("saas") Saas,
    @SerialName("e_book") EBook,
    @SerialName("edtech") EdTech,
    @SerialName("pdfs") Pdfs,
}

@Serializable
public sealed class PriceInput {
    @Serializable
    @SerialName("one_time")
    public data class OneTime(
        val currency: String,
        val discount: Int = 0,
        @SerialName("product_price") val productPrice: Int,
        @SerialName("purchasing_power_parity") val purchasingPowerParity: Boolean = false,
        @SerialName("suggested_price") val suggestedPrice: Int? = null,
        @SerialName("type") val type: String = "one_time",
    ) : PriceInput()

    @Serializable
    @SerialName("recurring")
    public data class Recurring(
        val currency: String,
        val discount: Int = 0,
        @SerialName("payment_frequency_count") val paymentFrequencyCount: Int,
        @SerialName("payment_frequency_interval") val paymentFrequencyInterval: String,
        @SerialName("product_price") val productPrice: Int,
        @SerialName("subscription_period_count") val subscriptionPeriodCount: Int,
        @SerialName("subscription_period_interval") val subscriptionPeriodInterval: String,
        @SerialName("purchasing_power_parity") val purchasingPowerParity: Boolean = false,
        @SerialName("trial_period_days") val trialPeriodDays: Int? = null,
        @SerialName("type") val type: String = "recurring",
    ) : PriceInput()
}

@Serializable
public data class CreateProductRequest(
    val name: String,
    @SerialName("tax_category") val taxCategory: TaxCategory,
    val price: PriceInput,
    val description: String? = null,
    @SerialName("brand_id") val brandId: String? = null,
    val metadata: Map<String, String>? = null,
    val addons: List<String>? = null,
    @SerialName("license_key_enabled") val licenseKeyEnabled: Boolean? = null,
    @SerialName("license_key_activations_limit") val licenseKeyActivationsLimit: Int? = null,
)

@Serializable
public data class Product(
    @SerialName("product_id") val productId: String,
    @SerialName("business_id") val businessId: String,
    val name: String,
    val description: String? = null,
    @SerialName("tax_category") val taxCategory: TaxCategory,
    @SerialName("is_recurring") val isRecurring: Boolean,
    val metadata: Map<String, String> = emptyMap(),
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val image: String? = null,
    @SerialName("brand_id") val brandId: String? = null,
    @SerialName("license_key_enabled") val licenseKeyEnabled: Boolean? = null,
)

@Serializable
public data class UpdateProductRequest(
    val name: String? = null,
    val description: String? = null,
    val metadata: Map<String, String>? = null,
    @SerialName("tax_category") val taxCategory: TaxCategory? = null,
)

@Serializable
public data class ListProductsParams(
    @SerialName("page_size") val pageSize: Int? = null,
    @SerialName("page_number") val pageNumber: Int? = null,
    val archived: Boolean? = null,
    val recurring: Boolean? = null,
    @SerialName("brand_id") val brandId: String? = null,
)
