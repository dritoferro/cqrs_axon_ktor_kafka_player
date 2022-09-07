package dev.tagliaferro.cqrs.player.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import dev.tagliaferro.cqrs.player.api.routePlayers

fun Application.configureRouting() {

    routing {
        routePlayers()
    }
}
