package com.conekta.mutants.controllers

import com.conekta.mutants.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class HealthControllerTest {

    @Test
    fun `get project health - OK`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/conekta/mutants/health").apply {
                MatcherAssert.assertThat(response.status(), CoreMatchers.`is`(HttpStatusCode.OK))
                MatcherAssert.assertThat(
                    response.content!!.lines().toString(),
                    CoreMatchers.`is`("""[{,   "status" : "UP",,   "version" : "local", }]""")
                )
            }
        }
    }

}