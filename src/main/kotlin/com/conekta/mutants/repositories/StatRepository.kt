package com.conekta.mutants.repositories

import com.conekta.mutants.dtos.StatResponseDTO
import com.conekta.mutants.entities.CountMutantsResult
import com.conekta.mutants.entities.DNAVerified
import com.conekta.mutants.entities.MongodbConfig
import com.conekta.mutants.utils.Constants
import com.conekta.mutants.utils.Constants.MONGO_COLLECTION_DNA_VERIFIED
import com.conekta.mutants.utils.NumberFormat.Companion.roundTotal
import com.conekta.mutants.utils.loggerFor
import com.typesafe.config.Config
import io.github.config4k.extract
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import org.litote.kmongo.match

class StatRepository(
    config: Config,
    private val mongoDataSource: CoroutineClient
) {
    private val mongoDatabase: String
    private val logger by lazy { loggerFor<StatRepository>() }

    init {
        val dbInfo: MongodbConfig = config.extract(Constants.APP_CONFIG_MONGODB)
        mongoDatabase = dbInfo.database
    }

    suspend fun getMutantStats(): StatResponseDTO {
        logger.debug("Get mutants stats in $MONGO_COLLECTION_DNA_VERIFIED")
        val collection = mongoDataSource
            .getDatabase(mongoDatabase)
            .getCollection<DNAVerified>(MONGO_COLLECTION_DNA_VERIFIED)

        val totalDocs = collection.countDocuments()
        val totalMutants = collection.aggregate<CountMutantsResult>(
            match(
                DNAVerified::isMutant eq true
            )
        ).toList().size.toLong()
        val totalHumans = totalDocs.minus(totalMutants)
        val ratio = roundTotal(totalMutants.toDouble().div(totalHumans.toDouble()))

        return StatResponseDTO(
            countMutantDna = totalMutants,
            countHumanDna = totalHumans,
            ratio = ratio
        )
    }
}