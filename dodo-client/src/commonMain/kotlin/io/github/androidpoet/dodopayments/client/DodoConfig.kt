package io.github.androidpoet.dodopayments.client

import io.github.androidpoet.dodopayments.core.DodoEnvironment
import io.ktor.client.plugins.logging.LogLevel

public data class DodoConfig(
    val environment: DodoEnvironment = DodoEnvironment.Test,
    val logging: Boolean = false,
    val logLevel: LogLevel = LogLevel.INFO,
)
