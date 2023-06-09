package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.example.plugins.configureRouting

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {

        application {
            configureRouting()
        }

        client.get("/customers/all").apply {
            println(bodyAsText())
//            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
        }

    }
}
