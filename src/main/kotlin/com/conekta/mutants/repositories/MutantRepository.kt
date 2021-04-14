package com.conekta.mutants.repositories

import com.conekta.mutants.entities.DNAVerified
import com.conekta.mutants.entities.MongodbConfig
import com.conekta.mutants.utils.Constants
import com.conekta.mutants.utils.loggerFor
import com.typesafe.config.Config
import io.github.config4k.extract
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*


class MutantRepository(
    config: Config,
    private val mongoDataSource: CoroutineClient
) {
    private val mongoDatabase: String
    private val logger by lazy { loggerFor<MutantRepository>() }

    init {
        val dbInfo: MongodbConfig = config.extract(Constants.APP_CONFIG_MONGODB)
        mongoDatabase = dbInfo.database
    }

    suspend fun addVerifiedDNA(dnaResult: DNAVerified) {
        logger.debug("adding a new record in $MONGO_COLLECTION_DNA_VERIFIED")
        mongoDataSource
            .getDatabase(mongoDatabase)
            .getCollection<DNAVerified>(MONGO_COLLECTION_DNA_VERIFIED)
            .insertOne(dnaResult)
    }

    companion object {
        const val MONGO_COLLECTION_DNA_VERIFIED = "dna_verified"
    }
}