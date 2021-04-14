package com.conekta.mutants.utils

import java.math.RoundingMode
import java.text.DecimalFormat

class NumberFormat {
    companion object {
        private const val DF2 = "#.#"

        fun roundTotal(number: Double, mode: RoundingMode? = null): Double {
            val roundMode = mode ?: RoundingMode.CEILING
            val df = DecimalFormat(DF2)
            df.roundingMode = roundMode

            return df.format(number).replaceFormat().toDouble()
        }

        private fun String.replaceFormat(): String {
            return this.replace(",", ".")
        }
    }
}