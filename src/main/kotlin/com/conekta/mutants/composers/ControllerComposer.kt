package com.conekta.mutants.composers

import com.conekta.mutants.controllers.HealthController
import com.conekta.mutants.controllers.MutantController
import com.conekta.mutants.controllers.StatController
import com.typesafe.config.Config
import io.ktor.routing.Route

class ControllerComposer(
    config: Config,
    route: Route,
    serviceComposer: ServiceComposer
) {
    val healthController = HealthController(config, route)
    val mutantController = MutantController(route, serviceComposer.mutantService)
    val statController = StatController(route, serviceComposer.statService)
}