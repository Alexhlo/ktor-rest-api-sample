package com.example

import com.example.dao.DatabaseFactory.initDb
import com.example.plugins.configureCors
import com.example.plugins.configureMetrics
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    initDb()

    configureSerialization()
    configureRouting()
    configureCors()
    configureMetrics()
}