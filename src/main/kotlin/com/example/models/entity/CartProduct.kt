package com.example.models.entity

import com.example.models.CommonModel
import kotlinx.serialization.Serializable

@Serializable
data class CartProduct(
    var cartId: Int? = null,
    var productId: Int? = null
): CommonModel(cartId)