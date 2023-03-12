package com.example.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

open class DAOTable(
    override val tableName: String,
    open val id: Column<Int>? = null
): Table(tableName)