package com.ahad.mail.sender.dto

data class EmailRequest(
    val name: String,
    val email: String,
    val subject: String,
    val message: String
)
