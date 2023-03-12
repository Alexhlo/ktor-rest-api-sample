package com.example.models.table

import com.example.models.DAOTable

object CartProductsTable: DAOTable("cart_products") {
    override val id = integer("id").autoIncrement()

    val cartId = integer("cart_id")
        .references(ref = CartsTable.id, fkName = "cart_id")
    val productId = integer("product_id")
        .references(ref = ProductsTable.id, fkName = "product_id")

    override val primaryKey = PrimaryKey(id)
}