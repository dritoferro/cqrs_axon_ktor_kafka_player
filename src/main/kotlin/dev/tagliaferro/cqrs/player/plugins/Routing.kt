package dev.tagliaferro.cqrs.player.plugins

import dev.tagliaferro.cqrs.player.api.routePlayers
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    routing {
        routePlayers()
    }
}
