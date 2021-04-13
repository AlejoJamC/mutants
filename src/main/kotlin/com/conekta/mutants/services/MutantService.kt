package com.conekta.mutants.services

import com.conekta.mutants.exceptions.MutantException
import com.conekta.mutants.utils.loggerFor
import kotlin.random.Random


class MutantService {
    private val logger by lazy { loggerFor<MutantService>() }

    suspend fun isMutant(dna: List<String>): Boolean {
        logger.debug("On isMutant for DNA frame: [$dna]")

        //TODO: define dna algorithm
        validateInput(dna)
        if (!validateDNA(dna)) throw MutantException.HumanDNAException()

        return true
    }

    private fun validateInput(dna: List<String>) {
        // validate allowed chars (A, T, C, G)
        val allowChars = dna.firstOrNull { it.contains("[^atcg]".toRegex(setOf(RegexOption.IGNORE_CASE))) }
        if (allowChars != null) throw MutantException.InvalidDNAChar()

        // validate symmetric information: the length of the stream is equals to list size
        val listSize = dna.size
        val symmetric = dna.firstOrNull { it.length != listSize }
        if (symmetric != null) throw MutantException.AsymmetricDNAData()

    }

    private fun validateDNA(dna: List<String>): Boolean {
        return Random.nextBoolean()
    }
}