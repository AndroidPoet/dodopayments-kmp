package io.github.androidpoet.dodopayments.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

internal actual fun createPlatformEngine(): HttpClientEngine = Js.create()
