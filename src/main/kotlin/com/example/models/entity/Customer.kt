package com.example.models.entity

import com.example.models.CommonModel
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    var firstName: String? = null,
    var lastName: String? = null,
    var phone: String? = null,
    var email: String? = null
) : CommonModel()