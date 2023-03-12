package com.example.dao.facades.impl

import com.example.dao.DAOFacade
import com.example.models.entity.Cart
import com.example.models.table.CartsTable
import com.example.utils.DAOFacadeUtil.deleteEntity
import com.example.utils.DAOFacadeUtil.getAllEntities
import com.example.utils.DAOFacadeUtil.getEntityById
import com.example.utils.DAOFacadeUtil.saveEntities
import com.example.utils.DAOFacadeUtil.saveEntity
import com.example.utils.DAOFacadeUtil.updateEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class CartDAOFacadeImpl: DAOFacade<Int, Cart> {

    override suspend fun getAll(): List<Cart> = getAllEntities(
        table = CartsTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun getById(id: Int): Cart? = getEntityById(
        incomingId = id,
        table = CartsTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun save(obj: Cart): Cart? = saveEntity(
        table = CartsTable,
        incomingEntity = obj,
        fillTableData = (::fillTableData),
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun saveAll(modelList: List<Cart>): Boolean = saveEntities(
        table = CartsTable,
        incomingEntityList = modelList,
        fillTableData = (::fillTableData)
    )

    override suspend fun update(obj: Cart): Boolean = updateEntity(
        table = CartsTable,
        incomingEntity = obj,
        fillTableData = (::fillTableData)
    )

    override suspend fun deleteById(id: Int): Boolean = deleteEntity(
        incomingId = id,
        table = CartsTable
    )

    private fun resultRowToModel(row: ResultRow): Cart {
        val model = Cart()

        model.id = row[CartsTable.id]
        model.customerId = row[CartsTable.customerId]

        return model
    }

    private fun fillTableData(obj: Cart, statement: UpdateBuilder<Number>) {
        obj.customerId.let { statement[CartsTable.customerId] = it!! }
    }
}