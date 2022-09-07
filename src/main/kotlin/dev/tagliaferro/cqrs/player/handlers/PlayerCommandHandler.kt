package dev.tagliaferro.cqrs.player.handlers

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.slf4j.LoggerFactory
import dev.tagliaferro.cqrs.player.commands.ContractPlayerCommand
import dev.tagliaferro.cqrs.player.events.PlayerContractedEvent
import java.util.UUID

private val players = mutableListOf<PlayerContractedEvent>()

class PlayerCommandHandler private constructor() {

    @AggregateIdentifier
    private lateinit var playerId: UUID

    private val logger = LoggerFactory.getLogger(javaClass)

    @CommandHandler
    constructor(command: ContractPlayerCommand) : this() {
        logger.info("Contracting new Player ${command.name}(${command.nationality}) and id ${command.id}")

        require(players.none { it.playerId == command.id }) { throw IllegalArgumentException("Player with this id already exists") }

        AggregateLifecycle.apply(PlayerContractedEvent.fromCommand(command))
    }

    @EventSourcingHandler
    fun playerContracted(event: PlayerContractedEvent) {
        playerId = event.playerId
        logger.info("New Player Contracted: {}", event)

        players.add(event)

        logger.info("Players count: ${players.size}")
    }
}
