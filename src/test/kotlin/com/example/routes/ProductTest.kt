package com.example.routes

import com.example.models.entity.Product
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

class ProductTest {

    @Test
    fun getAllProductTest() = testApplication {
        httpClient().get("$PATH/all").apply {
            logger.info("getAllProductTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun getProductByIdTest() = testApplication {
        httpClient().get("$PATH/{id}") {
            parameter("id", 1)
        }.apply {
            logger.info("getProductByIdTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveProductTest() = testApplication {
        httpClient().post("$PATH/save") {
            contentType(ContentType.Application.Json)
            setBody(initProduct())
        }.apply {
            logger.info("saveProductTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveAllProductsTest() = testApplication {
        httpClient().post("$PATH/save/all") {
            contentType(ContentType.Application.Json)
            setBody(mutableListOf(
                initProduct(name = "Iphone 10", price = 10000),
                initProduct(name = "Iphone 11", price = 11000),
                initProduct(name = "Iphone 12", price = 12000),
                initProduct(name = "Iphone 13", price = 13000),
                initProduct(name = "Iphone 14", price = 14000))
            )
        }.apply {
            logger.info("saveAllProductsTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun updateProductTest() = testApplication {
        httpClient().post("$PATH/update") {
            contentType(ContentType.Application.Json)
            setBody(initProduct(1, description = "Iphone 12", price = 1000))
        }.apply {
            logger.info("updateProductTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun deleteProductByIdTest() = testApplication {
        httpClient().delete("$PATH/") {
            parameter("id", 1)
        }.apply {
            logger.info("deleteProductByIdTest response = ${bodyAsText()}")
        }
    }

    private fun initProduct(
        id: Int? = null,
        name: String? = null,
        description: String? = null,
        price: Long? = null,
    ): Product {
        val product = Product()

        product.id = id
        product.name = name ?: "Iphone 11"
        product.description = description ?: "some description"
        product.price = price ?: 10000

        return product
    }

    private fun ApplicationTestBuilder.httpClient(): HttpClient = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        private const val PATH: String = "http://localhost:80/products"
    }
}