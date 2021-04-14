package com.conekta.mutants.entities

import com.conekta.mutants.utils.Constants.IS_MUTANT
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class DNAVerified(
    val dna: List<String>,
    @get:JsonProperty(IS_MUTANT) val isMutant: Boolean
)