package com.conekta.mutants.services

import com.conekta.mutants.exceptions.MutantException
import com.conekta.mutants.utils.loggerFor
import kotlin.random.Random


class MutantService {
    private val logger by lazy { loggerFor<MutantService>() }

    suspend fun isMutant(dna: List<String>): Boolean {
        logger.debug("On isMutant for DNA frame: [$dna]")

        //TODO: define dna algorithm
        if (!validateDNA(dna)) throw MutantException.HumanDNAException()

        return true
    }

    private fun validateDNA(dna: List<String>): Boolean {
        return Random.nextBoolean()
    }
}