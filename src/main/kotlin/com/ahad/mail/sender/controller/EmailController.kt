package com.ahad.mail.sender.controller

import com.ahad.mail.sender.dto.EmailRequest
import com.ahad.mail.sender.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/mail")
class EmailController(private val emailService: EmailService) {

    @PostMapping("/send")
    fun sendEmail(@RequestBody request: EmailRequest): ResponseEntity<Map<String, String>> {
        try {
            emailService.sendHtmlEmail(
                name = request.name,
                email = request.email,
                subject = request.subject,
                message = request.message
            )

            val response = mapOf(
                "status" to "success",
                "message" to "Email sent to receiver!"
            )

            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            val response = mapOf(
                "status" to "error",
                "message" to "Failed to send email: ${e.message}"
            )
            return ResponseEntity.status(500).body(response)
        }
    }
}
