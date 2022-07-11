package team.cqrs.dev.tagliaferro.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import team.cqrs.dev.tagliaferro.api.routePlayers

fun Application.configureRouting() {

    routing {
        routePlayers()
    }
}
