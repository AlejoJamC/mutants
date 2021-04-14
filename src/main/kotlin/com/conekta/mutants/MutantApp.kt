package com.conekta.mutants

import com.conekta.mutants.composers.RepositoryComposer
import com.conekta.mutants.composers.ServiceComposer
import com.conekta.mutants.composers.UtilComposer
import com.conekta.mutants.config.MongoConfig
import com.conekta.mutants.config.WebServerConfig
import com.typesafe.config.ConfigFactory
import io.ktor.application.*


fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val config = ConfigFactory.load()
    val utilComposer = UtilComposer(config)
    val mongoConfig = MongoConfig(config)
    val repositoryComposer = RepositoryComposer(config, mongoConfig.mongoDataClient)
    val serviceComposer = ServiceComposer(config, repositoryComposer)

    WebServerConfig(
        application = this,
        config = config,
        messages = utilComposer.messages,
        serviceComposer = serviceComposer
    )
}
