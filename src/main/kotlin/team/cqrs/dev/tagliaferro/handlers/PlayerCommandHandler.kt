package team.cqrs.dev.tagliaferro.handlers

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.slf4j.LoggerFactory
import team.cqrs.dev.tagliaferro.commands.ContractPlayerCommand
import team.cqrs.dev.tagliaferro.domain.Player
import team.cqrs.dev.tagliaferro.events.PlayerContractedEvent
import java.util.UUID

private val players = mutableListOf<Player>()

class PlayerCommandHandler private constructor() {

    @AggregateIdentifier
    private lateinit var playerId: UUID

    private val logger = LoggerFactory.getLogger(javaClass)

    @CommandHandler
    constructor(command: ContractPlayerCommand) : this() {
        val player = Player.fromCommand(command)
        logger.info("Contracting new Player ${player.name}(${player.nationality}) and id ${player.id}")
        players.add(player)

        AggregateLifecycle.apply(PlayerContractedEvent(player.id, player.name))
    }

    @EventSourcingHandler
    fun playerContracted(event: PlayerContractedEvent) {
        playerId = event.playerId
        logger.info("New Player Contracted: {}", event)
        logger.info("Players count: ${players.size}")
    }
}
