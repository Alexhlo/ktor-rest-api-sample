package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
    val swaggerHost = environment.config.property("swagger.host").getString()

    install(CORS) {
        allowHost(swaggerHost)
        allowHeader(HttpHeaders.ContentType)
    }
}