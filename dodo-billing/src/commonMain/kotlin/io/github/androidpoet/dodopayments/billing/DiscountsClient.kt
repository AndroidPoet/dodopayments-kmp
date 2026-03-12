package io.github.androidpoet.dodopayments.billing

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import io.github.androidpoet.dodopayments.core.PagedList
import kotlinx.serialization.json.Json

public class DiscountsClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createDiscount(request: CreateDiscountRequest): DodoResult<Discount> =
        client.post("/discounts", json.encodeToString(CreateDiscountRequest.serializer(), request))
            .map { json.decodeFromString<Discount>(it) }

    public suspend fun getDiscount(discountId: String): DodoResult<Discount> =
        client.get("/discounts/$discountId")
            .map { json.decodeFromString<Discount>(it) }

    public suspend fun listDiscounts(
        pageSize: Int? = null,
        pageNumber: Int? = null,
        code: String? = null,
    ): DodoResult<PagedList<Discount>> =
        client.get("/discounts", mapOf(
            "page_size" to pageSize?.toString(),
            "page_number" to pageNumber?.toString(),
            "code" to code,
        )).map { json.decodeFromString<PagedList<Discount>>(it) }

    public suspend fun deleteDiscount(discountId: String): DodoResult<String> =
        client.delete("/discounts/$discountId")

    public suspend fun validateDiscount(request: ValidateDiscountRequest): DodoResult<ValidateDiscountResponse> =
        client.post("/discounts/validate", json.encodeToString(ValidateDiscountRequest.serializer(), request))
            .map { json.decodeFromString<ValidateDiscountResponse>(it) }
}
