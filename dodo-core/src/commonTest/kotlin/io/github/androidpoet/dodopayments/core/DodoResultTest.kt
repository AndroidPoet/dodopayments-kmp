package io.github.androidpoet.dodopayments.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DodoResultTest {

    @Test
    fun test_success_map_transformsValue() {
        val result = DodoResult.Success(42)

        val mapped = result.map { it * 2 }

        assertIs<DodoResult.Success<Int>>(mapped)
        assertEquals(84, mapped.value)
    }

    @Test
    fun test_failure_map_propagatesError() {
        val error = DodoError(message = "not found", code = "404")
        val result: DodoResult<Int> = DodoResult.Failure(error)

        val mapped = result.map { it * 2 }

        assertIs<DodoResult.Failure>(mapped)
        assertEquals("not found", mapped.error.message)
    }

    @Test
    fun test_success_flatMap_chainsOperation() {
        val result = DodoResult.Success(10)

        val chained = result.flatMap { DodoResult.Success(it + 5) }

        assertIs<DodoResult.Success<Int>>(chained)
        assertEquals(15, chained.value)
    }

    @Test
    fun test_success_flatMap_propagatesInnerFailure() {
        val result = DodoResult.Success(10)
        val error = DodoError(message = "downstream error")

        val chained = result.flatMap { DodoResult.Failure(error) }

        assertIs<DodoResult.Failure>(chained)
        assertEquals("downstream error", chained.error.message)
    }

    @Test
    fun test_failure_recover_returnsDefaultValue() {
        val result: DodoResult<Int> = DodoResult.Failure(DodoError(message = "error"))

        val recovered = result.recover { 99 }

        assertIs<DodoResult.Success<Int>>(recovered)
        assertEquals(99, recovered.value)
    }

    @Test
    fun test_success_getOrNull_returnsValue() {
        val result = DodoResult.Success("hello")

        assertEquals("hello", result.getOrNull())
    }

    @Test
    fun test_failure_getOrNull_returnsNull() {
        val result: DodoResult<String> = DodoResult.Failure(DodoError(message = "error"))

        assertNull(result.getOrNull())
    }

    @Test
    fun test_failure_getOrElse_returnsDefault() {
        val result: DodoResult<String> = DodoResult.Failure(DodoError(message = "error"))

        assertEquals("default", result.getOrElse("default"))
    }

    @Test
    fun test_success_onSuccess_invokesAction() {
        val result = DodoResult.Success("value")
        var called = false

        result.onSuccess { called = true }

        assertTrue(called)
    }

    @Test
    fun test_failure_onFailure_invokesAction() {
        val error = DodoError(message = "boom")
        val result: DodoResult<String> = DodoResult.Failure(error)
        var capturedMessage = ""

        result.onFailure { capturedMessage = it.message }

        assertEquals("boom", capturedMessage)
    }

    @Test
    fun test_catching_wrapsExceptionAsFailure() {
        val result = DodoResult.catching<Int> { throw DodoException(DodoError(message = "caught")) }

        assertIs<DodoResult.Failure>(result)
        assertEquals("caught", result.error.message)
    }

    @Test
    fun test_catching_returnsSuccessOnNoException() {
        val result = DodoResult.catching { 42 }

        assertIs<DodoResult.Success<Int>>(result)
        assertEquals(42, result.value)
    }
}
