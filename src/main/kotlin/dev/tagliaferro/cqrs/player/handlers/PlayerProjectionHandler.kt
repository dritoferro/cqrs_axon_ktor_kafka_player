package dev.tagliaferro.cqrs.player.handlers

import dev.tagliaferro.cqrs.player.events.PlayerContractedEvent
import dev.tagliaferro.cqrs.player.exceptions.PlayerException
import dev.tagliaferro.cqrs.player.query.QueryByPlayerId
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.queryhandling.QueryHandler

class PlayerProjectionHandler(private val eventStore: EventStore) {

    @QueryHandler
    fun queryByPlayerId(queryByPlayerId: QueryByPlayerId): PlayerContractedEvent {
        val player = eventStore
            .readEvents(queryByPlayerId.playerId)
            .asStream()
            .map { it.payload }
            .findFirst()

        require(player.isPresent) { throw PlayerException("Player com o id ${queryByPlayerId.playerId} não encontrado ") }

        return player.get() as PlayerContractedEvent
    }
}
