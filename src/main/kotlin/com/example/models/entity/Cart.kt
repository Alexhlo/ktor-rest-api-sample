package com.example.models.entity

import com.example.models.CommonModel
import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    var customerId: Int? = null
): CommonModel()
