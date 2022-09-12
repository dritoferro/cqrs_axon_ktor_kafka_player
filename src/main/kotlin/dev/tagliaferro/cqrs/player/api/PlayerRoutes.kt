package dev.tagliaferro.cqrs.player.api

import dev.tagliaferro.cqrs.player.plugins.playerController
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.routePlayers() {
    post("/players") { playerController.createPlayer(call) }
    get("/players") { playerController.listPlayers(call) }
}
