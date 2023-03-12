package com.example.utils

import com.example.dao.DAOFacade
import com.example.models.CommonModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object RoutesUtil {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    suspend inline fun <reified T, D> PipelineContext<Unit, ApplicationCall>.getAllEntities(
        daoFacadeImpl: D
    ) where T : CommonModel, D: DAOFacade<Int, T> {
        val entities = daoFacadeImpl.getAll()

        if (entities.isEmpty()) {
            logger.warn("Request by ${call.request.uri} does not found entities")
            call.respond(HttpStatusCode.NotFound, "Entities not found!")
        } else {
            call.respond(entities)
        }
    }

    suspend inline fun <reified T, D> PipelineContext<Unit, ApplicationCall>.getEntityById(
        daoFacadeImpl: D
    ) where T : CommonModel, D: DAOFacade<Int, T> {
        val entityId = call.parameters["id"]
        val entity = entityId?.let { daoFacadeImpl.getById(it.toInt()) }

        if (entity == null) {
            logger.warn("Request by ${call.request.uri} does not found entity")
            call.respond(HttpStatusCode.NotFound, "Entity with id = $entityId not found!")
        } else
            call.respond(entity)
    }

    suspend inline fun <reified T, D>  PipelineContext<Unit, ApplicationCall>.saveAllEntities(
        daoFacadeImpl: D
    ) where T : CommonModel, D: DAOFacade<Int, T> {
        val entities = call.receive<List<T>>()
        val isSaveEntities = daoFacadeImpl.saveAll(entities)

        if (isSaveEntities) {
            call.respond(HttpStatusCode.OK, "Entities saved")
        } else {
            logger.warn("Request by ${call.request.uri} does not saved entities")
            call.respond(HttpStatusCode.InternalServerError, "Entities wasn't saved!")
        }
    }

    suspend inline fun <reified T, D> PipelineContext<Unit, ApplicationCall>.saveEntity(
        daoFacadeImpl: D
    ) where T : CommonModel, D: DAOFacade<Int, T> {
        val model = call.receive<T>()
        val savedModel = daoFacadeImpl.save(model)

        if (savedModel?.id == null) {
            logger.warn("Request by ${call.request.uri} does not saved entity with params: $savedModel")
            call.respond(HttpStatusCode.InternalServerError, "Entity wasn't saved!")
        } else {
            call.respond(savedModel)
        }
    }

    suspend inline fun <reified T, D> PipelineContext<Unit, ApplicationCall>.updateEntity(
        daoFacadeImpl: D
    ) where T : CommonModel, D: DAOFacade<Int, T> {
        val entity = call.receive<T>()
        val isUpdated = daoFacadeImpl.update(entity)

        if (isUpdated) {
            call.respond(HttpStatusCode.OK, "Entity was updated")
        } else {
            logger.warn("Request by ${call.request.uri} entity wasn't updated with params: $entity")
            call.respond(HttpStatusCode.NotFound, "Entity with id = ${entity.id} not found!")
        }
    }

    suspend inline fun <reified T, D> PipelineContext<Unit, ApplicationCall>.deleteEntityById(
        daoFacadeImpl: D
    ) where T : CommonModel, D: DAOFacade<Int, T> {
        val entityId = call.parameters["id"]
        val isDeleted = entityId?.let { daoFacadeImpl.deleteById(it.toInt()) }

        if (isDeleted!!) {
            call.respond(HttpStatusCode.OK, "Entity was deleted")
        } else {
            logger.warn("Request by ${call.request.uri} does not delete entity")
            call.respond(HttpStatusCode.NotFound, "Entity with id = $entityId not found!")
        }
    }
}