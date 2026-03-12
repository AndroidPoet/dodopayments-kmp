package io.github.androidpoet.dodopayments.client

import io.ktor.client.engine.HttpClientEngine

internal expect fun createPlatformEngine(): HttpClientEngine
