package com.example.dao

import com.example.models.config.DbConfig
import com.example.models.table.CartsTable
import com.example.models.table.CartProductsTable
import com.example.models.table.CustomersTable
import com.example.models.table.ProductsTable
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun Application.initDb() {
        val env = environment.config

        val config = DbConfig().getConfig(env).validateConfig()

        val database = Database.connect(
            url = config.url!!,
            driver = config.driver!!,
            user = config.username!!,
            password = config.password!!
        )

        transaction(database) {
            SchemaUtils.create(CustomersTable)
            SchemaUtils.create(ProductsTable)
            SchemaUtils.create(CartsTable)
            SchemaUtils.create(CartProductsTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
}