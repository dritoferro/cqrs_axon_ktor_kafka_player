package dev.tagliaferro.cqrs.player

import dev.tagliaferro.cqrs.player.plugins.AxonConfiguration
import dev.tagliaferro.cqrs.player.plugins.configureHTTP
import dev.tagliaferro.cqrs.player.plugins.configureHttpHandler
import dev.tagliaferro.cqrs.player.plugins.configureMonitoring
import dev.tagliaferro.cqrs.player.plugins.configureRouting
import dev.tagliaferro.cqrs.player.plugins.configureSerialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
//        configureSecurity()
        configureHTTP()
        configureHttpHandler()
        configureSerialization()
        configureMonitoring()
        AxonConfiguration.start()
    }.start(wait = true)
}
