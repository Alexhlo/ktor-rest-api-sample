package com.example.models.table

import com.example.models.DAOTable

object CartsTable: DAOTable("carts") {
    override val id = integer("id").autoIncrement()
    val customerId = integer("customer_id")
        .references(ref = CustomersTable.id, fkName = "customer_id")

    override val primaryKey = PrimaryKey(id)
}