package com.conekta.mutants.composers

import com.conekta.mutants.repositories.MutantRepository
import com.conekta.mutants.repositories.StatRepository
import com.typesafe.config.Config
import org.litote.kmongo.coroutine.CoroutineClient

class RepositoryComposer(config: Config, mongoClient: CoroutineClient) {
    val mutantRepository = MutantRepository(config, mongoClient)
    val statRepository = StatRepository(config, mongoClient)
}