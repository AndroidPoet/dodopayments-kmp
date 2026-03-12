package io.github.androidpoet.dodopayments.core

public enum class DodoEnvironment(public val baseUrl: String) {
    Test("https://test.dodopayments.com"),
    Live("https://live.dodopayments.com"),
}
