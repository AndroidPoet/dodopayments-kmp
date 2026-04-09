package io.github.androidpoet.dodopayments.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DodoEnvironmentTest {

    @Test
    fun test_testEnvironment_hasCorrectBaseUrl() {
        assertEquals("https://test.dodopayments.com", DodoEnvironment.Test.baseUrl)
    }

    @Test
    fun test_liveEnvironment_hasCorrectBaseUrl() {
        assertEquals("https://live.dodopayments.com", DodoEnvironment.Live.baseUrl)
    }

    @Test
    fun test_environments_haveDistinctBaseUrls() {
        assertNotEquals(DodoEnvironment.Test.baseUrl, DodoEnvironment.Live.baseUrl)
    }
}
