package com.conekta.mutants.controllers

import com.conekta.mutants.dtos.DNAFrameDTO
import com.conekta.mutants.dtos.StatResponseDTO
import com.conekta.mutants.services.StatService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

class StatController(
    router: Route,
    private val statService: StatService
) {
    init {
        router.route("/stats") {
            get {
                getMutantStats().let { call.respond(it) }
            }
        }
    }

    private suspend fun getMutantStats(): StatResponseDTO {
        return statService.getMutantStats()
    }
}