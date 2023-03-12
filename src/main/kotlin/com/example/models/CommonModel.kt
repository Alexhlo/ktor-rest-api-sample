package com.example.models

import kotlinx.serialization.Serializable

@Serializable
open class CommonModel(
    open var id: Int? = null
)

