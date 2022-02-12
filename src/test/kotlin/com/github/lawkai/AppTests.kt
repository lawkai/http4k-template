package com.github.lawkai

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AppTests {
    @Test
    @DisplayName("Test Hello World")
    fun testHelloWorld() {
        val req = Request(Method.GET, "/?name=world")
        assertThat(app(req), hasStatus(Status.OK).and(hasBody("Hello, world!")))
    }
}