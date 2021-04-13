package com.conekta.mutants.entities

import com.conekta.mutants.dtos.DNAFrameDTO


data class DNAFrame(
    val dna: List<String>
){
    fun toDTO() = DNAFrameDTO(
        dna = dna
    )
}
