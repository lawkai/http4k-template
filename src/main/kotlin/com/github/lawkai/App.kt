package com.github.lawkai

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

private const val PORT = 9_000

val app = { request: Request -> Response(OK).body("Hello, ${request.query("name")}!") }

fun main() {
    val server = app.asServer(Undertow(PORT)).start()

    val logger = LoggerFactory.getLogger("main")
    logger.info("Listening at port: {}", server.port())
}
