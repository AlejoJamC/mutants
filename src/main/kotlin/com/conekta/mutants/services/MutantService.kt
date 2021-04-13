package com.conekta.mutants.services

import com.conekta.mutants.utils.loggerFor


class MutantService {
    private val logger by lazy { loggerFor<MutantService>() }

    suspend fun isMutant(dna: List<String>): Boolean {
        logger.debug("On isMutant for DNA frame: [$dna]")

        //TODO: define dna algorithm

        return true
    }
}