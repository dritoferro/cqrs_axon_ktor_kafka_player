package dev.tagliaferro.cqrs.player.api

import dev.tagliaferro.cqrs.player.commands.ContractPlayerCommand
import dev.tagliaferro.cqrs.player.events.PlayerContractedEvent
import dev.tagliaferro.cqrs.player.query.QueryByPlayerId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.LoggerFactory

class PlayerController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun createPlayer(ctx: ApplicationCall) {
        logger.info("Received request to create player")

        val command = ctx.receive<ContractPlayerCommand>()
        commandGateway.send<Unit>(command)

        ctx.response.status(HttpStatusCode.Accepted)
    }

    suspend fun listPlayers(ctx: ApplicationCall) {
        val playerId = ctx.parameters["playerId"].toString()

        logger.info("Received request to get player by id $playerId")

        val result = withContext(dispatcher) {
            val queryByPlayerId = QueryByPlayerId(playerId = playerId)
            queryGateway.query(queryByPlayerId, PlayerContractedEvent::class.java).join()
        }

        ctx.respond(HttpStatusCode.OK, result)
    }
}
