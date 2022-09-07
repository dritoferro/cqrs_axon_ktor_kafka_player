package dev.tagliaferro.cqrs.player.api

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.LoggerFactory
import dev.tagliaferro.cqrs.player.commands.ContractPlayerCommand

class PlayerController(
    private val commandGateway: CommandGateway
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun createPlayer(ctx: ApplicationCall) {
        logger.info("Received request to create player")
        val command = ctx.receive<ContractPlayerCommand>()
        commandGateway.send<Unit>(command)
        ctx.response.status(HttpStatusCode.Accepted)
    }

    fun listPlayers(ctx: ApplicationCall) {
        //TODO not yet implemented
    }

}