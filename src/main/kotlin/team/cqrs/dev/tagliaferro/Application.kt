package team.cqrs.dev.tagliaferro

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import team.cqrs.dev.tagliaferro.plugins.configureHTTP
import team.cqrs.dev.tagliaferro.plugins.configureMonitoring
import team.cqrs.dev.tagliaferro.plugins.configureRouting
import team.cqrs.dev.tagliaferro.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
//        configureSecurity()
        configureHTTP()
        configureSerialization()
        configureMonitoring()
    }.start(wait = true)
}
