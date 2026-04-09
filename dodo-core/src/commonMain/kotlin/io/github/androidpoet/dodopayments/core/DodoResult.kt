package io.github.androidpoet.dodopayments.core

public sealed interface DodoResult<out T> {

    public data class Success<T>(val value: T) : DodoResult<T>

    public data class Failure(val error: DodoError) : DodoResult<Nothing>

    public fun <R> map(transform: (T) -> R): DodoResult<R> = when (this) {
        is Success -> Success(transform(value))
        is Failure -> this
    }

    public fun <R> flatMap(transform: (T) -> DodoResult<R>): DodoResult<R> = when (this) {
        is Success -> transform(value)
        is Failure -> this
    }

    public fun recover(transform: (DodoError) -> @UnsafeVariance T): DodoResult<T> = when (this) {
        is Success -> this
        is Failure -> Success(transform(error))
    }

    public fun onSuccess(action: (T) -> Unit): DodoResult<T> {
        if (this is Success) action(value)
        return this
    }

    public fun onFailure(action: (DodoError) -> Unit): DodoResult<T> {
        if (this is Failure) action(error)
        return this
    }

    public fun getOrNull(): T? = if (this is Success) value else null

    public fun getOrThrow(): T = when (this) {
        is Success -> value
        is Failure -> throw error.toException()
    }

    public companion object {
        public fun <T> catching(block: () -> T): DodoResult<T> = try {
            Success(block())
        } catch (e: DodoException) {
            Failure(e.error)
        } catch (e: Exception) {
            Failure(DodoError(message = e.message ?: "Unknown error"))
        }
    }
}

public fun <T> DodoResult<T>.getOrElse(default: T): T = when (this) {
    is DodoResult.Success -> value
    is DodoResult.Failure -> default
}
