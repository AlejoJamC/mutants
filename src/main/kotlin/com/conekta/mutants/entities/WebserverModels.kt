package com.conekta.mutants.entities

data class Mutant(
    val version: String,
    val locale: String,
    val countryCode: String,
) {
    init {
        require(version.isNotBlank()) { "Mutant version must not be blank" }
        require(locale.isNotBlank()) { "Mutant locale must not be blank" }
        require(countryCode.isNotBlank()) { "Mutant country code must not be blank" }
    }
}
