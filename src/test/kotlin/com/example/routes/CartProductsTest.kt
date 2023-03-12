package com.example.routes

import com.example.models.entity.CartProduct
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

class CartProductsTest {

    @Test
    fun getAllCartProductsTest() = testApplication {
        httpClient().get("$PATH/all").apply {
            logger.info("getAllCartProductsTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun getCartProductByIdTest() = testApplication {
        httpClient().get("$PATH/{id}") {
            parameter("id", 1)
        }.apply {
            logger.info("getCartProductByIdTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveCartProductTest() = testApplication {
        httpClient().post("$PATH/save") {
            contentType(ContentType.Application.Json)
            setBody(initCartProduct())
        }.apply {
            logger.info("saveCartProductTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveAllCartProductTest() = testApplication {
        httpClient().post("$PATH/save/all") {
            contentType(ContentType.Application.Json)
            setBody(mutableListOf(
                initCartProduct(cartId = 1, productId = 1),
                initCartProduct(cartId = 1, productId = 2),
                initCartProduct(cartId = 2, productId = 3))
            )
        }.apply {
            logger.info("saveAllCartProductTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun updateCartProductTest() = testApplication {
        httpClient().post("$PATH/update") {
            contentType(ContentType.Application.Json)
            setBody(initCartProduct(1, productId = 15))
        }.apply {
            logger.info("updateCartProductTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun deleteCartProductByIdTest() = testApplication {
        httpClient().delete("$PATH/") {
            parameter("id", 1)
        }.apply {
            logger.info("deleteCartProductByIdTest response = ${bodyAsText()}")
        }
    }

    private fun initCartProduct(
        id: Int? = null,
        cartId: Int? = null,
        productId: Int? = null,
    ): CartProduct {
        val cart = CartProduct()

        cart.id = id
        cart.cartId = cartId ?: 1
        cart.productId = productId ?: 1

        return cart
    }

    private fun ApplicationTestBuilder.httpClient(): HttpClient = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        private const val PATH: String = "http://localhost:80/cartProducts"
    }
}