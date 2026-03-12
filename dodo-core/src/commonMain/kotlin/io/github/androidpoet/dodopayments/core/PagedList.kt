package io.github.androidpoet.dodopayments.core

import kotlinx.serialization.Serializable

@Serializable
public data class PagedList<T>(
    val items: List<T>,
)

@Serializable
public data class CursorPagedList<T>(
    val data: List<T>,
    val done: Boolean,
    val iterator: String? = null,
    @kotlinx.serialization.SerialName("prev_iterator") val prevIterator: String? = null,
)
