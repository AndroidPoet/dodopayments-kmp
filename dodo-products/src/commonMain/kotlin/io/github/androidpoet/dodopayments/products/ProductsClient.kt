package io.github.androidpoet.dodopayments.products

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.core.DodoResult
import io.github.androidpoet.dodopayments.core.PagedList
import kotlinx.serialization.json.Json

public class ProductsClient(private val client: DodoClient) {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }

    public suspend fun createProduct(request: CreateProductRequest): DodoResult<Product> =
        client.post("/products", json.encodeToString(CreateProductRequest.serializer(), request))
            .map { json.decodeFromString<Product>(it) }

    public suspend fun getProduct(productId: String): DodoResult<Product> =
        client.get("/products/$productId")
            .map { json.decodeFromString<Product>(it) }

    public suspend fun listProducts(params: ListProductsParams = ListProductsParams()): DodoResult<PagedList<Product>> =
        client.get("/products", params.toQueryParams())
            .map { json.decodeFromString<PagedList<Product>>(it) }

    public suspend fun updateProduct(productId: String, request: UpdateProductRequest): DodoResult<Product> =
        client.patch("/products/$productId", json.encodeToString(UpdateProductRequest.serializer(), request))
            .map { json.decodeFromString<Product>(it) }

    public suspend fun archiveProduct(productId: String): DodoResult<String> =
        client.post("/products/$productId/archive", "{}")

    public suspend fun unarchiveProduct(productId: String): DodoResult<String> =
        client.post("/products/$productId/unarchive", "{}")

    private fun ListProductsParams.toQueryParams(): Map<String, String?> = mapOf(
        "page_size" to pageSize?.toString(),
        "page_number" to pageNumber?.toString(),
        "archived" to archived?.toString(),
        "recurring" to recurring?.toString(),
        "brand_id" to brandId,
    )
}
