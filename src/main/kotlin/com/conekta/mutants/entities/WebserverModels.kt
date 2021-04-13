package com.conekta.mutants.entities

data class Mutant(
    val version: String,
    val locale: String,
    val countryCode: String,
    val maxSequenceAllowed: String,
) {
    init {
        require(version.isNotBlank()) { "Mutant version must not be blank" }
        require(locale.isNotBlank()) { "Mutant locale must not be blank" }
        require(countryCode.isNotBlank()) { "Mutant country code must not be blank" }
        require(maxSequenceAllowed.isNotBlank()) { "Max DNA sequence allowed by human must not be blank" }
    }
}
