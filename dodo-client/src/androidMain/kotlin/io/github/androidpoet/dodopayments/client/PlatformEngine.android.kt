package io.github.androidpoet.dodopayments.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun createPlatformEngine(): HttpClientEngine = OkHttp.create()
