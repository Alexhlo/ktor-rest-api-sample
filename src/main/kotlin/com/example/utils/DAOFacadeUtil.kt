package com.example.utils

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.CommonModel
import com.example.models.DAOTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object DAOFacadeUtil {

    suspend inline fun <T : DAOTable, M : CommonModel> getAllEntities(
        table: T,
        crossinline resultRowTo: (ResultRow) -> M
    ): List<M> = dbQuery {
        table.selectAll()
            .map { resultRowTo(it) }
    }

    suspend inline fun <T: DAOTable, M: CommonModel> getEntityById(
        table: T,
        incomingId: Int,
        crossinline resultRowTo: (ResultRow) -> M
    ) : M? = dbQuery {
        table.select { table.id!! eq incomingId }
            .map { resultRowTo(it) }
            .singleOrNull()
    }

    suspend inline fun <T: DAOTable, M: CommonModel> saveEntities(
        table: T,
        incomingEntityList: List<M>,
        crossinline fillTableData: (obj: M, statement: UpdateBuilder<Number>) -> Unit
    ): Boolean = dbQuery {
        table.batchInsert(
            data = incomingEntityList,
            body = { fillTableData(it, this) }
        ).isNotEmpty()
    }

    suspend inline fun <T: DAOTable, M: CommonModel> saveEntity(
        table: T,
        incomingEntity: M,
        crossinline resultRowTo: (ResultRow) -> M,
        crossinline fillTableData: (M, UpdateBuilder<Number>) -> Unit
    ): M? = dbQuery {

        table.insert {
            fillTableData(incomingEntity, it) }
            .resultedValues
            ?.singleOrNull()
            ?.let { resultRowTo(it) }
    }

    suspend inline fun <T: DAOTable, M: CommonModel> updateEntity(
        table: T,
        incomingEntity: M,
        crossinline fillTableData: (M, UpdateBuilder<Number>) -> Unit,
    ): Boolean = dbQuery {
        if (incomingEntity.id == null) throw RuntimeException("Cannot update entity without id!")

        table.update( { table.id!! eq incomingEntity.id!! } ) {
            fillTableData(incomingEntity, it)
        } > 0
    }

    suspend inline fun <T: DAOTable> deleteEntity(
        table: T,
        incomingId: Int
    ): Boolean = dbQuery {
        table.deleteWhere { table.id!! eq incomingId } > 0
    }
}