package com.example.plugins

import com.example.dao.facades.impl.CartDAOFacadeImpl
import com.example.dao.facades.impl.CartProductDAOFacadeImpl
import com.example.dao.facades.impl.CustomerDAOFacadeImpl
import com.example.dao.facades.impl.ProductDAOFacadeImpl
import com.example.routes.cartProductRouting
import com.example.routes.cartRouting
import com.example.routes.customerRouting
import com.example.routes.productRouting
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        cartRouting(CartDAOFacadeImpl())
        productRouting(ProductDAOFacadeImpl())
        customerRouting(CustomerDAOFacadeImpl())
        cartProductRouting(CartProductDAOFacadeImpl())

        swaggerUI(
            path =  "swagger",
            swaggerFile = "swagger/documentation.json"
        ) {
            version = "4.15.5"
        }
    }
}