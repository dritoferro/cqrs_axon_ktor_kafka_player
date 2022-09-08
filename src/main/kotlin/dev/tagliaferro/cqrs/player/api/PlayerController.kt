package dev.tagliaferro.cqrs.player.api

import dev.tagliaferro.cqrs.player.commands.ContractPlayerCommand
import dev.tagliaferro.cqrs.player.domain.Player
import dev.tagliaferro.cqrs.player.query.QueryByPlayerId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.LoggerFactory

class PlayerController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun createPlayer(ctx: ApplicationCall) {
        logger.info("Received request to create player")
        val command = ctx.receive<ContractPlayerCommand>()
        commandGateway.send<Unit>(command)
        ctx.response.status(HttpStatusCode.Accepted)
    }

    suspend fun listPlayers(ctx: ApplicationCall) {
        logger.info("Received request to get player")
        val queryByPlayerId = QueryByPlayerId(playerId = "a24a14f9-cb1f-43a8-8833-c64c31ecaa59")

        val result = withContext(Dispatchers.IO) {
            queryGateway.query(queryByPlayerId, ResponseTypes.instanceOf(Player::class.java)).get()
        }

        ctx.respond(HttpStatusCode.OK, result)
    }
}