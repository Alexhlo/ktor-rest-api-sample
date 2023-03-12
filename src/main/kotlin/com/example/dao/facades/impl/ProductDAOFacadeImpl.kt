package com.example.dao.facades.impl

import com.example.dao.DAOFacade
import com.example.models.entity.Product
import com.example.models.table.ProductsTable
import com.example.utils.DAOFacadeUtil.deleteEntity
import com.example.utils.DAOFacadeUtil.getAllEntities
import com.example.utils.DAOFacadeUtil.getEntityById
import com.example.utils.DAOFacadeUtil.saveEntities
import com.example.utils.DAOFacadeUtil.saveEntity
import com.example.utils.DAOFacadeUtil.updateEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class ProductDAOFacadeImpl: DAOFacade<Int, Product> {

    override suspend fun getAll(): List<Product> = getAllEntities(
        table = ProductsTable,
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun getById(id: Int): Product? = getEntityById(
        incomingId = id,
        table = ProductsTable,
        resultRowTo =  (::resultRowToModel)
    )

    override suspend fun saveAll(modelList: List<Product>): Boolean = saveEntities(
        table = ProductsTable,
        incomingEntityList = modelList,
        fillTableData = (::fillTableData)
    )

    override suspend fun save(obj: Product): Product? = saveEntity(
        incomingEntity =  obj,
        table =  ProductsTable,
        fillTableData =  (::fillTableData),
        resultRowTo = (::resultRowToModel)
    )

    override suspend fun update(obj: Product): Boolean = updateEntity(
        incomingEntity = obj,
        table = ProductsTable,
        fillTableData = (::fillTableData)
    )

    override suspend fun deleteById(id: Int): Boolean = deleteEntity(
        table = ProductsTable,
        incomingId = id
    )

    private fun resultRowToModel(row: ResultRow): Product {
        val model = Product()

        model.id = row[ProductsTable.id]
        model.name = row[ProductsTable.name]
        model.description = row[ProductsTable.description]
        model.price = row[ProductsTable.price]

        return model
    }

    private fun fillTableData(obj: Product, statement: UpdateBuilder<Number>)  {
        obj.name.let { statement[ProductsTable.name] = it!! }
        obj.description.let { statement[ProductsTable.description] = it!! }
        obj.price.let { statement[ProductsTable.price] = it!! }
    }
}