package dev.tagliaferro.cqrs.player.plugins

import dev.tagliaferro.cqrs.player.api.PlayerController

val playerController = PlayerController(
    AxonConfiguration.getCommandGateway(),
    AxonConfiguration.getQueryGateway()
)
