package com.example.routes

import com.example.models.entity.Customer
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.Test
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@TestMethodOrder(OrderAnnotation::class)
class CustomerTest {

    @Test
    @Order(1)
    fun saveCustomerTest() = testApplication {
        httpClient().post("$PATH/save") {
            contentType(ContentType.Application.Json)
            setBody(initCustomer())
        }.apply {
            logger.info("getAllCustomersTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun getCustomerByIdTest() = testApplication {
        httpClient().get("$PATH/{id}") {
            parameter("id", 11410)
        }.apply {
            logger.info("getCustomerByIdTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun saveAllCustomersTest() = testApplication {
        httpClient().post("$PATH/save/all") {
            contentType(ContentType.Application.Json)
            setBody(mutableListOf(
                initCustomer(firstName = "Alex", lastName = "Storm", phone = "8(999)-999-99-99", email = "adubua@gmail.com"),
                initCustomer(firstName = "Hannah", lastName = "Bristol", phone = "8(098)-765-43-21", email = "bhannah@mail.com"),
                initCustomer(firstName = "Frank", lastName = "Colombo", phone = "8(123)-456-78-90"),
                initCustomer(firstName = "Harry", lastName = "Potter", email = "hpotter@hogwarts.mail.com"))
            )
        }.apply {
            logger.info("saveAllCustomersTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun getAllCustomersTest() = testApplication {
        httpClient().get("$PATH/all").apply {
            logger.info("getAllCustomersTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun updateCustomerTest() = testApplication {
        httpClient().post("$PATH/update") {
            contentType(ContentType.Application.Json)
            setBody(initCustomer(1, firstName = "Frodo", lastName = "Bag", phone = "", email = ""))
        }.apply {
            logger.info("updateCustomerTest response = ${bodyAsText()}")
        }
    }

    @Test
    fun deleteCustomerByIdTest() = testApplication {
        httpClient().delete("$PATH/") {
            parameter("id", 11409)
        }.apply {
            logger.info("deleteCustomerTest response = ${bodyAsText()}")
        }
    }

    private fun initCustomer(
        id: Int? = null,
        firstName: String? = null,
        lastName: String? = null,
        phone: String? = null,
        email: String? = null
    ): Customer {
        val customer = Customer()

        customer.id = id
        customer.firstName = firstName ?: "John"
        customer.lastName = lastName ?: "Doe"
        customer.phone = phone ?: "+7(111)-111-11-11"
        customer.email = email ?: "jdoe@mail.com"

        return customer
    }

    private fun ApplicationTestBuilder.httpClient(): HttpClient = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)

        private const val PATH: String = "http://localhost:80/customers"
    }
}