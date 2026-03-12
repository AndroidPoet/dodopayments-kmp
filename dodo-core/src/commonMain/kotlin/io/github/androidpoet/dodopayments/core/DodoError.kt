package io.github.androidpoet.dodopayments.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DodoError(
    val message: String,
    val code: String? = null,
    @SerialName("error_type") val errorType: String? = null,
    val details: String? = null,
) {
    public fun toException(): DodoException = DodoException(this)
}

public class DodoException(
    public val error: DodoError,
) : Exception(error.message)
