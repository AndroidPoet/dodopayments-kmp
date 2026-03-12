package io.github.androidpoet.dodopayments.client

import io.github.androidpoet.dodopayments.core.DodoEnvironment
import org.koin.dsl.module

public fun dodoModule(
    apiKey: String,
    environment: DodoEnvironment = DodoEnvironment.Test,
    configure: DodoConfig.() -> Unit = {},
) = module {
    single<DodoClient> { DodoPayments.create(apiKey, environment, configure) }
}
