package com.example.routes

import com.example.dao.DAOFacade
import com.example.dao.facades.impl.CartDAOFacadeImpl
import com.example.models.entity.Cart
import com.example.utils.RouteInterceptorUtil.interceptPossibleError
import com.example.utils.RoutesUtil.deleteEntityById
import com.example.utils.RoutesUtil.getAllEntities
import com.example.utils.RoutesUtil.getEntityById
import com.example.utils.RoutesUtil.saveAllEntities
import com.example.utils.RoutesUtil.saveEntity
import com.example.utils.RoutesUtil.updateEntity
import io.ktor.server.routing.*

fun Route.cartRouting(dao: DAOFacade<Int, Cart>) {
    route("/carts") {
        interceptPossibleError<Cart>()

        get("/all") { getAllEntities(dao) }

        get("{id}") { getEntityById(dao) }

        post("/save") { saveEntity(dao) }

        post("/save/all") { saveAllEntities(dao) }

        post("/update") { updateEntity(dao) }

        delete("/{id}") { deleteEntityById(dao) }
    }
}