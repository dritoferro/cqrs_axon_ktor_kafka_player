package dev.tagliaferro.cqrs.player

import dev.tagliaferro.cqrs.player.plugins.AxonConfiguration
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import dev.tagliaferro.cqrs.player.plugins.configureHTTP
import dev.tagliaferro.cqrs.player.plugins.configureMonitoring
import dev.tagliaferro.cqrs.player.plugins.configureRouting
import dev.tagliaferro.cqrs.player.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
//        configureSecurity()
        configureHTTP()
        configureSerialization()
        configureMonitoring()
        AxonConfiguration.start()
    }.start(wait = true)
}
