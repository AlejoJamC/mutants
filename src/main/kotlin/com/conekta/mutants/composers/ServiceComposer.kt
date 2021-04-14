package com.conekta.mutants.composers

import com.conekta.mutants.services.MutantService
import com.conekta.mutants.services.StatService
import com.typesafe.config.Config

class ServiceComposer(config: Config, repositoryComposer: RepositoryComposer) {
    val mutantService = MutantService(config, repositoryComposer.mutantRepository)
    val statService = StatService(repositoryComposer.statRepository)
}