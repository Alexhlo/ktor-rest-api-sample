package com.example.models.entity

import com.example.models.CommonModel
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    var name: String? = null,
    var description: String? = null,
    var price: Long? = null
): CommonModel()
