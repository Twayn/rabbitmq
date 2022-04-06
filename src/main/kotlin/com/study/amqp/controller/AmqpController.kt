package com.study.amqp.controller

import com.study.amqp.model.PowRequest
import com.study.amqp.persist.AmqpRepository
import com.study.amqp.persist.PowResult
import com.study.amqp.queue.api.AmqpSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AmqpController(
    private val sender: AmqpSender,
    private val messageStorage: AmqpRepository
) {
    var logger: Logger = LoggerFactory.getLogger(AmqpController::class.java)

    @PostMapping("/sendRequest")
    fun sendMessage(@RequestBody request: PowRequest):String {
        logger.info("Got request: $request")

        sender.send(request)

        logger.info("Message sent: $request")

        return "Message successfully sent: $request"
    }

    @GetMapping("/getProcessingResults")
    fun getAllMessages(): Iterable<PowResult> {
        return messageStorage.findAll()
    }
}