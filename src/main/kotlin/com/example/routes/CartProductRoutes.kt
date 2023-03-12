package com.example.routes

import com.example.dao.DAOFacade
import com.example.dao.facades.impl.CartProductDAOFacadeImpl
import com.example.models.entity.CartProduct
import com.example.utils.RouteInterceptorUtil.interceptPossibleError
import com.example.utils.RoutesUtil.deleteEntityById
import com.example.utils.RoutesUtil.getAllEntities
import com.example.utils.RoutesUtil.getEntityById
import com.example.utils.RoutesUtil.saveAllEntities
import com.example.utils.RoutesUtil.saveEntity
import com.example.utils.RoutesUtil.updateEntity
import io.ktor.server.routing.*

fun Route.cartProductRouting(dao: DAOFacade<Int, CartProduct>) {
    route("/cartProducts") {
        interceptPossibleError<CartProduct>()

        get("/all") { getAllEntities(dao) }

        get("{id}") { getEntityById(dao) }

        post("/save") { saveEntity(dao) }

        post("/save/all") { saveAllEntities(dao) }

        post("/update") { updateEntity(dao) }

        delete("/{id}") { deleteEntityById(dao) }
    }
}