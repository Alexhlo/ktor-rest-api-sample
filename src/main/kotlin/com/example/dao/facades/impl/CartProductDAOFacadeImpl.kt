package com.example.dao.facades.impl

import com.example.dao.DAOFacade
import com.example.models.entity.CartProduct
import com.example.models.table.CartProductsTable
import com.example.utils.DAOFacadeUtil.deleteEntity
import com.example.utils.DAOFacadeUtil.getAllEntities
import com.example.utils.DAOFacadeUtil.getEntityById
import com.example.utils.DAOFacadeUtil.saveEntities
import com.example.utils.DAOFacadeUtil.saveEntity
import com.example.utils.DAOFacadeUtil.updateEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class CartProductDAOFacadeImpl: DAOFacade<Int, CartProduct> {

    override suspend fun getAll(): List<CartProduct> = getAllEntities(
        table = CartProductsTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun getById(id: Int): CartProduct? = getEntityById(
        incomingId = id,
        table = CartProductsTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun save(obj: CartProduct): CartProduct? = saveEntity(
        incomingEntity = obj,
        table = CartProductsTable,
        fillTableData = (::fillTableData),
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun saveAll(modelList: List<CartProduct>): Boolean = saveEntities(
        table = CartProductsTable,
        incomingEntityList = modelList,
        fillTableData = (::fillTableData)
    )

    override suspend fun update(obj: CartProduct): Boolean = updateEntity(
        table = CartProductsTable,
        incomingEntity = obj,
        fillTableData = (::fillTableData)
    )

    override suspend fun deleteById(id: Int): Boolean = deleteEntity(
        incomingId = id,
        table = CartProductsTable
    )

    private fun resultRowToModel(row: ResultRow): CartProduct {
        val model = CartProduct()

        model.id = row[CartProductsTable.id]
        model.cartId = row[CartProductsTable.cartId]
        model.productId = row[CartProductsTable.productId]

        return model
    }

    private fun fillTableData(obj: CartProduct, statement: UpdateBuilder<Number>) {
        obj.cartId.let { statement[CartProductsTable.cartId] = it!! }
        obj.productId.let { statement[CartProductsTable.productId] = it!! }
    }
}