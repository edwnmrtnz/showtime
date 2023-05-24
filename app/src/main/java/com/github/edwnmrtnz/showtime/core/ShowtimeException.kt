package com.github.edwnmrtnz.showtime.core

sealed class ShowtimeException(override val message: String) : Exception(message) {

    class IOException(
        private val throwable: Throwable
    ) : ShowtimeException(throwable.message ?: "")

    class HttpException(
        val code: Int,
        message: String
    ) : ShowtimeException(message)

    class DomainException(message: String) : ShowtimeException(message)
}
