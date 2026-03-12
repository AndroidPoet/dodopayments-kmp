# Dodo Payments KMP

Kotlin Multiplatform SDK for the [Dodo Payments](https://dodopayments.com) API. Coroutine-first, type-safe, zero-overhead.

## Platforms

Android · iOS · macOS · JVM · Linux · Windows · WebAssembly

## Installation

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.github.androidpoet:dodo-client:0.1.0")
    implementation("io.github.androidpoet:dodo-payments:0.1.0")
    implementation("io.github.androidpoet:dodo-subscriptions:0.1.0")
    implementation("io.github.androidpoet:dodo-customers:0.1.0")
    implementation("io.github.androidpoet:dodo-products:0.1.0")
    implementation("io.github.androidpoet:dodo-billing:0.1.0")
}
```

## Quick Start

```kotlin
val client = DodoPayments.create(
    apiKey = "your_api_key",
    environment = DodoEnvironment.Test,
) {
    logging = true
}

val payments = PaymentsClient(client)
val subscriptions = SubscriptionsClient(client)
val customers = CustomersClient(client)
val products = ProductsClient(client)
val refunds = RefundsClient(client)
val discounts = DiscountsClient(client)

// Create a payment
val result = payments.createPayment(
    CreatePaymentRequest(
        productCart = listOf(ProductCartItem("product_id", 1)),
        customer = CustomerInput(email = "user@example.com", name = "Jane Doe"),
        billing = BillingInput(country = "US"),
    )
)

result
    .onSuccess { println("Payment: ${it.paymentId}") }
    .onFailure { println("Error: ${it.message}") }
```

## Modules

| Module | Description |
|--------|-------------|
| `dodo-core` | Foundation types: `DodoResult`, `DodoError`, value class IDs |
| `dodo-client` | Ktor HTTP transport, `DodoClient` interface, Koin module |
| `dodo-payments` | Payments + Checkout Sessions |
| `dodo-subscriptions` | Subscriptions, plan changes, on-demand charges |
| `dodo-customers` | Customer management + portal sessions |
| `dodo-products` | Products + Addons |
| `dodo-billing` | Refunds + Discounts |

## License

MIT
