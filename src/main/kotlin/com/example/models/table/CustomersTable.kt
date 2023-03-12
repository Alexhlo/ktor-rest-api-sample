package com.example.models.table

import com.example.models.DAOTable

object CustomersTable: DAOTable(tableName = "customers") {
    override val id = CustomersTable.integer("id").autoIncrement()
    val firstName = varchar("first_name", 128)
    val lastName = varchar("last_name", 128)
    val phone = varchar("phone", 30)
    val email = varchar("email", 128)

    override val primaryKey = PrimaryKey(id)
}