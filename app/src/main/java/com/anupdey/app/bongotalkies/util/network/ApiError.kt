package com.anupdey.app.bongotalkies.util.network

import androidx.annotation.StringRes

data class ApiError(
    val type: ErrorType,
    val message: String,
    @StringRes val messageId: Int = 0
)

enum class ErrorType {
    NOT_FOUND,
    NO_DATA,
    VALIDATION_ERROR,
    INTERNAL_SERVER_ERROR,
    UNAUTHORIZED,
    FORBIDDEN,
    FAILURE,
    NETWORK
}
