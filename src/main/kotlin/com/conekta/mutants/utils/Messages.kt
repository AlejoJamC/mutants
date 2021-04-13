package com.conekta.mutants.utils

import com.conekta.mutants.utils.Constants.ES
import java.util.Locale
import java.util.Properties


class Messages(locale: Locale) {
    private var properties: Properties

    init {
        val fileName = getMessagesFileName(locale.language)
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResourceAsStream(fileName)

        require(resource != null) { "File $fileName not found" }
        properties = Properties().apply {
            load(resource)
        }
    }

    fun get(resourceName: String, args: List<String>?): String {
        var resource = properties.getProperty(resourceName)
        args?.forEachIndexed { index, s ->
            resource = resource.replace("{$index}", s)
        }

        return resource
    }

    private fun getMessagesFileName(language: String) = when (language.toLowerCase()) {
        ES -> "${BASE_FILE_NAME}_$ES$PROPERTIES_EXTENSION"
        else -> "${BASE_FILE_NAME}$PROPERTIES_EXTENSION"
    }

    companion object {
        private const val BASE_FILE_NAME = "messages"
        private const val PROPERTIES_EXTENSION = ".properties"
    }
}