package com.conekta.mutants.composers

import com.conekta.mutants.services.MutantService
import com.typesafe.config.Config


class ServiceComposer(config: Config) {
    val mutantService = MutantService(config)
}