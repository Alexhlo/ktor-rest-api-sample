package com.example.models.table

import com.example.models.DAOTable

object ProductsTable: DAOTable("products") {
    override val id = integer("id").autoIncrement()
    val name = varchar("name", 256)
    val description = text("description")
    val price = long("price")

    override val primaryKey = PrimaryKey(id)
}