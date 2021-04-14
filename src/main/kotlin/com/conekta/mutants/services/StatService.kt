package com.conekta.mutants.services

import com.conekta.mutants.dtos.StatResponseDTO
import com.conekta.mutants.repositories.StatRepository
import com.conekta.mutants.utils.loggerFor

class StatService(private val statRepository: StatRepository) {

    private val logger by lazy { loggerFor<MutantService>() }

    suspend fun getMutantStats(): StatResponseDTO {
        logger.debug("On getMutantStats")
        return statRepository.getMutantStats()
    }
}