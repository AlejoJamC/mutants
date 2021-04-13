package com.conekta.mutants.services

import com.conekta.mutants.entities.Mutant
import com.conekta.mutants.exceptions.MutantException
import com.conekta.mutants.utils.Constants.APP_CONFIG_MUTANT
import com.conekta.mutants.utils.loggerFor
import com.typesafe.config.Config
import io.github.config4k.extract
import java.lang.RuntimeException


class MutantService(private val config: Config) {
    private val logger by lazy { loggerFor<MutantService>() }

    suspend fun isMutant(dna: List<String>): Boolean {
        logger.debug("On isMutant for DNA frame: [$dna]")

        validateInput(dna)
        if (!validateDNA(dna)) throw MutantException.HumanDNAException()

        return true
    }

    private fun validateInput(dna: List<String>) {
        // validate allowed chars (A, T, C, G)
        val allowChars = dna.firstOrNull { it.contains("[^atcg]".toRegex(setOf(RegexOption.IGNORE_CASE))) }
        if (allowChars != null) {
            logger.debug("On validateInput: InvalidDNAChar")
            throw MutantException.InvalidDNAChar()
        }

        // validate symmetric information: the length of the stream is equals to list size
        val listSize = dna.size
        val symmetric = dna.firstOrNull { it.length != listSize }
        if (symmetric != null) {
            logger.debug("On validateInput: AsymmetricDNAData")
            throw MutantException.AsymmetricDNAData()
        }

    }

    private fun validateDNA(dna: List<String>): Boolean = try {
        // early return with horizontal validation
        var count = 0
        dna.forEach {
            if (it.contains(AAAA, ignoreCase = true)
                || it.contains(TTTT, ignoreCase = true)
                || it.contains(CCCC, ignoreCase = true)
                || it.contains(GGGG, ignoreCase = true)
            ) {
                logger.debug("On validateDNA: finding horizontal")
                ++count
            }
            if (count > 1) {
                logger.debug("On validateDNA: early return horizontal")
                return true
            }
        }

        // putting together an nxn matrix, checking verticals and diagonals
        val maxSequenceAllowed = config.extract<Mutant>(APP_CONFIG_MUTANT).maxSequenceAllowed.toInt()

        var dnaArray = arrayOf<CharArray>()

        dna.forEach {
            val innerArray = it.toCharArray()
            dnaArray += innerArray
        }

        for (i in 0..dna.lastIndex) {
            for (j in 0..dna.lastIndex) {
                if (searchTopToDown(i, j, CONSECUTIVE_CHARS, dna.lastIndex, dnaArray)) {
                    logger.debug("On matrix: searchTopToDown")
                    countingOrEscape(count, maxSequenceAllowed)
                }
                if (searchBackSlash(i, j, CONSECUTIVE_CHARS, dna.lastIndex, dnaArray)) {
                    logger.debug("On matrix: searchBackSlash")
                    countingOrEscape(count, maxSequenceAllowed)
                }
                if (searchSlash(i, j, CONSECUTIVE_CHARS, dna.lastIndex, dnaArray)) {
                    logger.debug("On matrix: searchSlash")
                    countingOrEscape(count, maxSequenceAllowed)
                }
            }
        }

        false
    } catch (ex: MutantException.MaxDNASequenceException) {
        true
    } catch (ex: Exception) {
        throw RuntimeException()
    }

    private fun searchTopToDown(i: Int, j: Int, consecutive: Int, lastIndex: Int, arr: Array<CharArray>): Boolean {
        if (i.plus(consecutive) <= lastIndex) {
            if (arr[i][j] == arr[i.plus(1)][j] && arr[i][j] == arr[i.plus(2)][j] && arr[i][j] == arr[i.plus(3)][j]) {
                return true
            }
        }

        return false
    }

    private fun searchBackSlash(i: Int, j: Int, consecutive: Int, lastIndex: Int, arr: Array<CharArray>): Boolean {
        if (i.plus(consecutive) <= lastIndex && j.plus(consecutive) <= lastIndex) {
            if (arr[i][j] == arr[i.plus(1)][j.plus(1)] && arr[i][j] == arr[i.plus(2)][j.plus(2)] && arr[i][j] == arr[i.plus(
                    3
                )][j.plus(3)]
            ) {
                return true
            }
        }

        return false
    }

    private fun searchSlash(i: Int, j: Int, consecutive: Int, lastIndex: Int, arr: Array<CharArray>): Boolean {
        if (i.minus(consecutive.plus(1)) >= 0 && j.plus(consecutive) <= lastIndex) {
            if (arr[i][j] == arr[i.minus(1)][j.plus(1)] && arr[i][j] == arr[i.minus(2)][j.plus(2)] && arr[i][j] == arr[i.minus(
                    3
                )][j.plus(3)]
            ) {
                return true
            }
        }

        return false
    }

    private fun countingOrEscape(counter: Int, criteria: Int): Int {
        val nextVal = counter.plus(1)

        return if (nextVal > criteria) {
            throw MutantException.MaxDNASequenceException()
        } else {
            nextVal
        }
    }

    companion object {
        private const val CONSECUTIVE_CHARS = 4
        private const val AAAA = "AAAA"
        private const val TTTT = "TTTT"
        private const val CCCC = "CCCC"
        private const val GGGG = "GGGG"
    }
}