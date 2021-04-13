package com.conekta.mutants.controllers

import com.conekta.mutants.entities.Mutant
import com.conekta.mutants.utils.Constants.APP_CONFIG_MUTANT
import com.typesafe.config.Config
import io.github.config4k.extract
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route


class HealthController(
    config: Config,
    route: Route,
) {
    private val version: String

    init {

        val orchestratorSettings: Mutant = config.extract(APP_CONFIG_MUTANT)
        version = orchestratorSettings.version

        route.route("/health") {
            get {
                call.respond(mapOf("status" to "UP", "version" to version))
            }
        }

    }
}