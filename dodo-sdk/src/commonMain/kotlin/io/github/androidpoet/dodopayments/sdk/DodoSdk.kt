package io.github.androidpoet.dodopayments.sdk

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

/**
 * Aggregate SDK facade with manual dependency wiring.
 *
 * No DI framework is used. You can still instantiate individual clients directly.
 */
public class DodoSdk(
    public val client: DodoClient,
) {
    public val payments: PaymentsClient = PaymentsClient(client)
    public val checkout: CheckoutClient = CheckoutClient(client)
    public val subscriptions: SubscriptionsClient = SubscriptionsClient(client)
    public val customers: CustomersClient = CustomersClient(client)
    public val products: ProductsClient = ProductsClient(client)
    public val refunds: RefundsClient = RefundsClient(client)
    public val discounts: DiscountsClient = DiscountsClient(client)

    public fun close() {
        client.close()
    }

    public companion object {
        public fun create(
            apiKey: String,
            environment: DodoEnvironment = DodoEnvironment.Test,
            configure: DodoConfig.() -> Unit = {},
        ): DodoSdk {
            val client = DodoPayments.create(
                apiKey = apiKey,
                environment = environment,
                configure = configure,
            )
            return DodoSdk(client)
        }
    }
}
