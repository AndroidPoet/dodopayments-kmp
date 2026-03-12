package io.github.androidpoet.dodopayments.koin

import io.github.androidpoet.dodopayments.billing.DiscountsClient
import io.github.androidpoet.dodopayments.billing.RefundsClient
import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.client.DodoConfig
import io.github.androidpoet.dodopayments.client.DodoPayments
import io.github.androidpoet.dodopayments.core.DodoEnvironment
import io.github.androidpoet.dodopayments.customers.CustomersClient
import io.github.androidpoet.dodopayments.payments.CheckoutClient
import io.github.androidpoet.dodopayments.payments.PaymentsClient
import io.github.androidpoet.dodopayments.products.ProductsClient
import io.github.androidpoet.dodopayments.subscriptions.SubscriptionsClient
import org.koin.dsl.koinApplication
import org.koin.dsl.module

/**
 * Aggregate SDK entry point. Koin is used internally as an isolated
 * instance — it never touches the host app's Koin context.
 *
 * Usage:
 * ```
 * val sdk = DodoSdk(apiKey = "your_key", environment = DodoEnvironment.Test)
 * sdk.payments.createPayment(...)
 * sdk.subscriptions.listSubscriptions()
 * ```
 */
public class DodoSdk(
    apiKey: String,
    environment: DodoEnvironment = DodoEnvironment.Test,
    configure: DodoConfig.() -> Unit = {},
) {
    // Isolated Koin instance — never calls startKoin(), never touches host app DI
    private val di = koinApplication {
        modules(
            module {
                single<DodoClient> { DodoPayments.create(apiKey, environment, configure) }
                single { PaymentsClient(get()) }
                single { CheckoutClient(get()) }
                single { SubscriptionsClient(get()) }
                single { CustomersClient(get()) }
                single { ProductsClient(get()) }
                single { RefundsClient(get()) }
                single { DiscountsClient(get()) }
            }
        )
    }

    public val payments: PaymentsClient = di.koin.get()
    public val checkout: CheckoutClient = di.koin.get()
    public val subscriptions: SubscriptionsClient = di.koin.get()
    public val customers: CustomersClient = di.koin.get()
    public val products: ProductsClient = di.koin.get()
    public val refunds: RefundsClient = di.koin.get()
    public val discounts: DiscountsClient = di.koin.get()

    public fun close() {
        di.koin.get<DodoClient>().close()
    }
}
