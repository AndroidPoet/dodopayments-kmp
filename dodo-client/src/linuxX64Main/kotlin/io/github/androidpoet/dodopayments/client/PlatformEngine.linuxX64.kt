package io.github.androidpoet.dodopayments.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

internal actual fun createPlatformEngine(): HttpClientEngine = CIO.create()
