package dev.tagliaferro.cqrs.player.handlers

import dev.tagliaferro.cqrs.player.commands.ContractPlayerCommand
import dev.tagliaferro.cqrs.player.domain.Player
import dev.tagliaferro.cqrs.player.events.PlayerContractedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.AggregateMember
import org.slf4j.LoggerFactory
import java.util.UUID

class PlayerCommandHandler private constructor() {

    @AggregateIdentifier
    private lateinit var playerId: UUID

    @AggregateMember
    lateinit var player: Player

    private val logger = LoggerFactory.getLogger(javaClass)

    @CommandHandler
    constructor(player: ContractPlayerCommand) : this() {
        logger.info("Contracting new Player ${player.name}(${player.nationality}) and id ${player.id}")

        require(player.name.isNotBlank()) { throw IllegalArgumentException("Player name cannot be empty") }

        AggregateLifecycle.apply(PlayerContractedEvent.fromCommand(player))
    }

    @EventSourcingHandler
    fun handlePlayerContracted(event: PlayerContractedEvent) {
        val createdPlayer = Player.fromEvent(event)
        playerId = createdPlayer.playerId
        player = createdPlayer

        logger.info("New Player Contracted: {}", createdPlayer)
    }
}
