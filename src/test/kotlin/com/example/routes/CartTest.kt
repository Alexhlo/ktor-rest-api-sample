package com.example.routes

import com.example.models.entity.Cart
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CartTest {

    @Test
    fun getAllCartsTest() = testApplication {
        httpClient().get("$PATH/all").apply {
            logger.info("getAllCartsTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun getCartByIdTest() = testApplication {
        httpClient().get("$PATH/{id}") {
            parameter("id", 1)
        }.apply {
            logger.info("getCartByIdTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveCartTest() = testApplication {
        httpClient().post("$PATH/save") {
            contentType(ContentType.Application.Json)
            setBody(initCart())
        }.apply {
            logger.info("saveCartTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveAllCartsTest() = testApplication {
        httpClient().post("$PATH/save/all") {
            contentType(ContentType.Application.Json)
            setBody(mutableListOf(
                initCart(customerId = 1),
                initCart(customerId = 2),
                initCart(customerId = 3))
            )
        }.apply {
            logger.info("saveAllCartsTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun updateCartTest() = testApplication {
        httpClient().post("$PATH/update") {
            contentType(ContentType.Application.Json)
            setBody(initCart(1, customerId = 15))
        }.apply {
            logger.info("updateCartTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun deleteCartByIdTest() = testApplication {
        httpClient().delete("$PATH/") {
            parameter("id", 1)
        }.apply {
            logger.info("deleteCartByIdTest response = ${bodyAsText()}")
        }
    }

    private fun initCart(
        id: Int? = null,
        customerId: Int? = null,
    ): Cart {
        val cart = Cart()

        cart.id = id
        cart.customerId = customerId ?: 1

        return cart
    }

    private fun ApplicationTestBuilder.httpClient(): HttpClient = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        private const val PATH: String = "http://localhost:80/carts"
    }
}