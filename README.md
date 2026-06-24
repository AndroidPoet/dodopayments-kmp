<p align="center">
  <img src="art/logo.png" alt="dodopayments-kmp" />
</p>

# Dodo Payments KMP

Kotlin Multiplatform SDK for the [Dodo Payments](https://dodopayments.com) API. Coroutine-first, type-safe, zero runtime overhead.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.androidpoet/dodo-client)](https://central.sonatype.com/search?q=io.github.androidpoet.dodo)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.10-purple.svg)](https://kotlinlang.org)

## Platforms

| Platform | Support |
|----------|---------|
| Android | ✅ |
| iOS (arm64, x64, simulatorArm64) | ✅ |
| macOS (arm64, x64) | ✅ |
| JVM | ✅ |
| Linux (x64) | ✅ |
| Windows (mingwX64) | ✅ |
| WebAssembly (wasmJs) | ✅ |

## Installation

Add the modules you need to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.androidpoet:dodo-client:0.1.0")

    implementation("io.github.androidpoet:dodo-payments:0.1.0")
    implementation("io.github.androidpoet:dodo-subscriptions:0.1.0")
    implementation("io.github.androidpoet:dodo-customers:0.1.0")
    implementation("io.github.androidpoet:dodo-products:0.1.0")
    implementation("io.github.androidpoet:dodo-billing:0.1.0")

    implementation("io.github.androidpoet:dodo-sdk:0.1.0")
}
```

## Quick Start

### Option A — Direct clients

```kotlin
val client = DodoPayments.create(
    apiKey = "your_api_key",
    environment = DodoEnvironment.Test, // or DodoEnvironment.Live
) {
    logging = true
}

val payments     = PaymentsClient(client)
val subscriptions = SubscriptionsClient(client)
val customers    = CustomersClient(client)
val products     = ProductsClient(client)
val refunds      = RefundsClient(client)
val discounts    = DiscountsClient(client)
```

### Option B — DodoSdk aggregate facade

All clients pre-wired in a single object.

```kotlin
val sdk = DodoSdk.create(
    apiKey = "your_api_key",
    environment = DodoEnvironment.Test,
) {
    logging = true
}

sdk.payments.createPayment(...)
sdk.subscriptions.createSubscription(...)
sdk.customers.getCustomer(...)
sdk.products.listProducts()
sdk.refunds.createRefund(...)
sdk.discounts.validateDiscount(...)

sdk.close()
```

## Usage

### Payments

```kotlin
val result = payments.createPayment(
    CreatePaymentRequest(
        productCart = listOf(ProductCartItem(productId = "prod_123", quantity = 1)),
        customer = CustomerInput(email = "jane@example.com", name = "Jane Doe"),
        billing = BillingInput(country = "US"),
        returnUrl = "https://yourapp.com/success",
    )
)

result
    .onSuccess { println("Payment created: ${it.paymentId}, secret: ${it.clientSecret}") }
    .onFailure { println("Error: ${it.message}") }

payments.listPayments(ListPaymentsParams(pageSize = 20, status = "succeeded"))
    .onSuccess { it.items.forEach { p -> println(p.paymentId) } }

payments.getPayment("pay_abc123")
```

### Subscriptions

```kotlin
subscriptions.createSubscription(
    CreateSubscriptionRequest(
        productId = "prod_456",
        quantity = 1,
        customer = CustomerInput(email = "jane@example.com", name = "Jane Doe"),
        billing = BillingInput(country = "US"),
        trialPeriodDays = 14,
    )
)

subscriptions.cancelSubscription("sub_xyz")

subscriptions.changePlan(
    "sub_xyz",
    ChangePlanRequest(
        productId = "prod_789",
        quantity = 1,
        prorationBillingMode = "prorated_immediately",
    )
)

subscriptions.chargeOnDemand(
    "sub_xyz",
    OnDemandChargeRequest(productPrice = 1000, productCurrency = "USD"),
)
```

### Customers

```kotlin
customers.createCustomer(CreateCustomerRequest(name = "Jane Doe", email = "jane@example.com"))

customers.updateCustomer("cust_123", UpdateCustomerRequest(name = "Jane Smith"))

customers.createPortalSession("cust_123")
    .onSuccess { println("Portal URL: ${it.link}") }
```

### Products

```kotlin
products.createProduct(
    CreateProductRequest(
        name = "Pro Plan",
        taxCategory = TaxCategory.Saas,
        price = PriceInput.OneTime(currency = "USD", productPrice = 4900),
    )
)

products.createProduct(
    CreateProductRequest(
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
)

products.archiveProduct("prod_123")
products.unarchiveProduct("prod_123")
```

### Refunds & Discounts

```kotlin
refunds.createRefund(CreateRefundRequest(paymentId = "pay_abc123"))

refunds.createRefund(
    CreateRefundRequest(
        paymentId = "pay_abc123",
        items = listOf(PartialRefundItem(itemId = "item_1", amount = 500)),
        reason = "Customer request",
    )
)

discounts.createDiscount(
    CreateDiscountRequest(
        amount = 2000, // 20% in basis points
        code = "SAVE20",
        usageLimit = 100,
    )
)

discounts.validateDiscount(ValidateDiscountRequest(code = "SAVE20"))
    .onSuccess { if (it.valid) println("Valid! ${it.discount?.amount}bps off") }
```

### Error handling

All operations return `DodoResult<T>` — a sealed type with no exceptions:

```kotlin
when (val result = payments.getPayment("pay_abc123")) {
    is DodoResult.Success -> println(result.value.status)
    is DodoResult.Failure -> println("${result.error.code}: ${result.error.message}")
}

payments.getPayment("pay_abc123")
    .map { it.totalAmount / 100.0 }
    .onSuccess { println("$$it") }
    .onFailure { println(it.message) }
    .getOrElse(0.0)
```

## Modules

| Module | Artifact | Description |
|--------|----------|-------------|
| `dodo-core` | `io.github.androidpoet:dodo-core` | `DodoResult<T>`, `DodoError`, typed ID value classes, shared models |
| `dodo-client` | `io.github.androidpoet:dodo-client` | Ktor HTTP transport, `DodoPayments.create()` factory |
| `dodo-payments` | `io.github.androidpoet:dodo-payments` | Payments + Checkout Sessions |
| `dodo-subscriptions` | `io.github.androidpoet:dodo-subscriptions` | Subscriptions, plan changes, on-demand charges |
| `dodo-customers` | `io.github.androidpoet:dodo-customers` | Customer management + portal sessions |
| `dodo-products` | `io.github.androidpoet:dodo-products` | Products (one-time + recurring) |
| `dodo-billing` | `io.github.androidpoet:dodo-billing` | Refunds + Discounts |
| `dodo-sdk` | `io.github.androidpoet:dodo-sdk` | Optional aggregate facade |

## Environments

```kotlin
DodoEnvironment.Test  // https://test.dodopayments.com
DodoEnvironment.Live  // https://live.dodopayments.com
```

## Contributing

Contributions are welcome! If you've found a bug, have an idea for an improvement, or want to contribute new features, please open an issue or submit a pull request.

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/androidpoet/dodopayments-kmp/stargazers)__ for this repository. :star: <br>
Also, __[follow me](https://github.com/androidpoet)__ on GitHub for my next creations! 🤩

## License

```
MIT License

Copyright (c) 2025 Ranbir Singh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
