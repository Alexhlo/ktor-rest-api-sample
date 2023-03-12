package com.example.models.config

import io.ktor.server.config.*

data class DbConfig(
    val driver: String? = null,
    val url: String? = null,
    val username: String? = null,
    val password: String? = null
) {

    fun getConfig(envConfig: ApplicationConfig?): DbConfig {
        return DbConfig(
            driver = envConfig?.property("db.config.driver")?.getString(),
            url = envConfig?.property("db.config.jdbcURL")?.getString(),
            username = envConfig?.property("db.config.username")?.getString(),
            password = envConfig?.property("db.config.password")?.getString()
        )
    }

    fun validateConfig(): DbConfig {
        val isNotExistsDriver = this.driver.isNullOrEmpty()
        val isNotExistsUrl = this.url.isNullOrEmpty()
        val isNotExistsUsername = this.username.isNullOrEmpty()
        val isNotExistsPassword = this.password.isNullOrEmpty()

        if (isNotExistsDriver || isNotExistsUrl || isNotExistsUsername || isNotExistsPassword) {
            throw RuntimeException("Db config does not exists!")
        } else {
            return this;
        }
    }
}
