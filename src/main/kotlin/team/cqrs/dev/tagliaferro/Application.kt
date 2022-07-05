package team.cqrs.dev.tagliaferro

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import team.cqrs.dev.tagliaferro.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureSerialization()
        configureMonitoring()
    }.start(wait = true)
}
