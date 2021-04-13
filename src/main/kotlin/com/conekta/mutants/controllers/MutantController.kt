package com.conekta.mutants.controllers

import com.conekta.mutants.dtos.DNAFrameDTO
import com.conekta.mutants.services.MutantService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


class MutantController(
    router: Route,
    private val mutantService: MutantService
) {
    init {
        router.route("") {
            post {
                val dndFrames = call.receive<DNAFrameDTO>()
                call.respond(HttpStatusCode.OK, isMutant(dndFrames))
            }
        }
    }

    private suspend fun isMutant(frames: DNAFrameDTO): Boolean {
        return mutantService.isMutant(frames.toEntity().dna)
    }
}