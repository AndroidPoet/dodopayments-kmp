package io.github.androidpoet.dodopayments.koin

import io.github.androidpoet.dodopayments.client.DodoClient
import io.github.androidpoet.dodopayments.client.DodoConfig
import io.github.androidpoet.dodopayments.client.DodoPayments
import io.github.androidpoet.dodopayments.core.DodoEnvironment
import org.koin.dsl.module

public fun dodoModule(
    apiKey: String,
    environment: DodoEnvironment = DodoEnvironment.Test,
    configure: DodoConfig.() -> Unit = {},
) = module {
    single<DodoClient> { DodoPayments.create(apiKey, environment, configure) }
}
