package com.example.utils

import com.example.models.CommonModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object RouteInterceptorUtil {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    inline fun <reified T: CommonModel> Route.interceptPossibleError() {
        intercept(ApplicationCallPipeline.Call) {
            try {
                logger.info("Incoming request ${call.request.httpMethod.value} ${call.request.uri}")
                proceed()
            } catch (err: Throwable) {
                logger.error("Error with message: ${err.message}", err)
                throw err
            } finally {
                call.respond(HttpStatusCode.InternalServerError, "${T::class.java.simpleName} route error")
            }
        }
    }
}



