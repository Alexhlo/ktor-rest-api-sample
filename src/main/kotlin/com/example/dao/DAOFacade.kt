package com.example.dao

interface DAOFacade<K, T> {

    suspend fun getAll(): List<T>

    suspend fun getById(id: K): T?

    suspend fun save(obj: T): T?

    suspend fun saveAll(modelList: List<T>): Boolean

    suspend fun update(obj: T): Boolean

    suspend fun deleteById(id: K): Boolean
}