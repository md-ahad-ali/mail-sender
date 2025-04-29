package com.ahad.mail.sender.service

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.receiver}") private val receiver: String
) {

    fun sendHtmlEmail(name: String, email: String, subject: String, message: String) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name())

        val emailHtml = loadEmailTemplate()
            .replace("{{name}}", name)
            .replace("{{email}}", email)
            .replace("{{subject}}", subject)
            .replace("{{message}}", message)

        helper.setTo(receiver)
        helper.setSubject(subject)
        helper.setText(emailHtml, true)

        mailSender.send(mimeMessage)
    }

    private fun loadEmailTemplate(): String {
        val resource = ClassPathResource("static/email-template.html")
        return resource.inputStream.bufferedReader().use { it.readText() }
    }
}
