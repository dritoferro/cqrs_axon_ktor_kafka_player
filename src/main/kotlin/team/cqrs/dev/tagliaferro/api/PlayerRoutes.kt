package team.cqrs.dev.tagliaferro.api

import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import team.cqrs.dev.tagliaferro.plugins.playerController

fun Route.routePlayers() {
    post("/players") { playerController.createPlayer(call) }
    get("/players") { playerController.listPlayers(call) }
}