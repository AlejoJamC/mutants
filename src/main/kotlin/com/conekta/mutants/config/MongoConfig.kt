package com.conekta.mutants.config

import com.conekta.mutants.entities.MongodbConfig
import com.conekta.mutants.utils.Constants.APP_CONFIG_MONGODB
import com.typesafe.config.Config
import io.github.config4k.extract
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class MongoConfig(config: Config) {
    var mongoDataClient: CoroutineClient

    init {
        val dbInfo: MongodbConfig = config.extract(APP_CONFIG_MONGODB)

        mongoDataClient = KMongo.createClient(
            "mongodb://${dbInfo.host}:${dbInfo.port}"
        ).coroutine
    }
}