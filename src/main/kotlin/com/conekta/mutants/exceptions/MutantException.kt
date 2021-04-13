package com.conekta.mutants.exceptions

import com.conekta.mutants.entities.ApplicationError
import io.ktor.http.*

sealed class MutantException(
    override var httpsStatus: HttpStatusCode,
    override var code: String,
    override var args: List<String>? = null
) : ApplicationError(httpsStatus, code, args) {
    class HumanDNAException : MutantException(HttpStatusCode.Forbidden, "error.dna.human")
}