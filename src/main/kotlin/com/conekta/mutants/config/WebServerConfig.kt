package com.conekta.mutants.config

import com.conekta.mutants.composers.ControllerComposer
import com.conekta.mutants.composers.ServiceComposer
import com.conekta.mutants.entities.ApiError
import com.conekta.mutants.entities.ApplicationError
import com.conekta.mutants.entities.ErrorInfo
import com.conekta.mutants.utils.Constants.MUTANT_BASE_URI
import com.conekta.mutants.utils.Messages
import com.conekta.mutants.utils.loggerFor
import com.fasterxml.jackson.databind.SerializationFeature
import com.typesafe.config.Config
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.route
import io.ktor.routing.routing


class WebServerConfig(
    application: Application,
    config: Config,
    messages: Messages,
    serviceComposer: ServiceComposer
) {
    private val logger by lazy { loggerFor<WebServerConfig>() }

    init {
        application.apply {
            // Install Ktor content negotiation feature
            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
            }

            // Intercepts errors
            install(StatusPages) {
                exception<ApplicationError> { cause ->
                    logger.error("Intercepting Error", cause)
                    call.respond(
                        cause.httpsStatus, ApiError(
                            cause.httpsStatus,
                            ErrorInfo(
                                cause.code,
                                messages.get(cause.code, cause.args)
                            )
                        )
                    )
                }
            }

            // Base project routing
            routing {
                route(MUTANT_BASE_URI) {
                    ControllerComposer(config = config, route = this, serviceComposer = serviceComposer)
                }
            }
        }
    }
}