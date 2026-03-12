package io.github.androidpoet.dodopayments.client

import io.github.androidpoet.dodopayments.core.DodoEnvironment

public object DodoPayments {
    public fun create(
        apiKey: String,
        environment: DodoEnvironment = DodoEnvironment.Test,
        configure: DodoConfig.() -> Unit = {},
    ): DodoClient {
        val config = DodoConfig(environment = environment).apply(configure)
        return HttpTransport(
            apiKey = apiKey,
            baseUrl = environment.baseUrl,
            config = config,
        )
    }
}
