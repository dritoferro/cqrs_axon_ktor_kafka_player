package team.cqrs.dev.tagliaferro.plugins

import team.cqrs.dev.tagliaferro.api.PlayerController

val playerController = PlayerController(commandGatewayBuilder())