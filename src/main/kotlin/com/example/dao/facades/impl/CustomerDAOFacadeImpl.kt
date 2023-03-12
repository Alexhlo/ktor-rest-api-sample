package com.example.dao.facades.impl

import com.example.dao.DAOFacade
import com.example.models.entity.Customer
import com.example.models.table.CustomersTable
import com.example.utils.DAOFacadeUtil
import com.example.utils.DAOFacadeUtil.deleteEntity
import com.example.utils.DAOFacadeUtil.getAllEntities
import com.example.utils.DAOFacadeUtil.getEntityById
import com.example.utils.DAOFacadeUtil.saveEntity
import com.example.utils.DAOFacadeUtil.updateEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class CustomerDAOFacadeImpl : DAOFacade<Int, Customer> {

    override suspend fun getAll(): List<Customer> = getAllEntities(
        table =  CustomersTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun getById(id: Int): Customer? = getEntityById(
        incomingId = id,
        table = CustomersTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun saveAll(modelList: List<Customer>): Boolean = DAOFacadeUtil.saveEntities(
        table = CustomersTable,
        incomingEntityList = modelList,
        fillTableData = (::fillTableData)
    )

    override suspend fun save(obj: Customer): Customer? = saveEntity(
        incomingEntity =  obj,
        table =  CustomersTable,
        fillTableData =  (::fillTableData),
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun update(obj: Customer): Boolean = updateEntity(
        incomingEntity = obj,
        table = CustomersTable,
        fillTableData = (::fillTableData)
    )

    override suspend fun deleteById(id: Int): Boolean = deleteEntity(
        table = CustomersTable,
        incomingId = id
    )

    private fun resultRowToModel(row: ResultRow): Customer {
        val model = Customer()

        model.id = row[CustomersTable.id]
        model.firstName = row[CustomersTable.firstName]
        model.lastName = row[CustomersTable.lastName]
        model.phone = row[CustomersTable.phone]
        model.email = row[CustomersTable.email]

        return model
    }

    private fun fillTableData(obj: Customer, statement: UpdateBuilder<Number>) {
        obj.firstName.let { statement[CustomersTable.firstName] = it!! }
        obj.lastName.let { statement[CustomersTable.lastName] = it!! }
        obj.phone.let { statement[CustomersTable.phone] = it!! }
        obj.email.let { statement[CustomersTable.email] = it!! }
    }
}