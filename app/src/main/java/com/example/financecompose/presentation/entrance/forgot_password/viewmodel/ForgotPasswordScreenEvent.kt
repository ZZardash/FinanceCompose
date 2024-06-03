package com.example.financecompose.presentation.entrance.forgot_password.viewmodel

sealed class ForgotPasswordScreenEvent {
    data class SendPasswordResetMail(
        val userMail: String = ""
    ): ForgotPasswordScreenEvent()
}