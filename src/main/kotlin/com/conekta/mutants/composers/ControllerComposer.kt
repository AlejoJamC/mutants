package com.conekta.mutants.composers

import com.conekta.mutants.controllers.HealthController
import com.typesafe.config.Config
import io.ktor.routing.Route


class ControllerComposer(
    config: Config,
    route: Route,
) {
    val healthController = HealthController(config, route)
}