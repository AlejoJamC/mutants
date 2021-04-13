package com.conekta.mutants.composers

import com.conekta.mutants.entities.Mutant
import com.conekta.mutants.utils.Constants.APP_CONFIG_MUTANT
import com.conekta.mutants.utils.Messages
import com.typesafe.config.Config
import io.github.config4k.extract
import java.util.Locale


class UtilComposer(config: Config) {
    private val locale: Locale
    val messages: Messages

    init {
        val localInfo: Mutant = config.extract(APP_CONFIG_MUTANT)
        locale = Locale(localInfo.locale, localInfo.countryCode)
        messages = Messages(locale)
    }
}