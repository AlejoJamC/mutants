package com.conekta.mutants.dtos

import com.conekta.mutants.entities.DNAFrame
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class DNAFrameDTO(
    val dna: List<String>
) {
    fun toEntity() = DNAFrame(
        dna = dna
    )
}
