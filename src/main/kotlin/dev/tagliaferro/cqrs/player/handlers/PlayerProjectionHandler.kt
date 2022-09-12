package dev.tagliaferro.cqrs.player.handlers

import dev.tagliaferro.cqrs.player.domain.Player
import dev.tagliaferro.cqrs.player.query.QueryByPlayerId
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.queryhandling.QueryHandler
import java.util.concurrent.CompletableFuture

class PlayerProjectionHandler(eventStore: EventStore) {

    private val repository = EventSourcingRepository
        .builder(PlayerCommandHandler::class.java)
        .eventStore(eventStore)
        .build<EventSourcingRepository<PlayerCommandHandler>>()

    @QueryHandler
    fun queryByPlayerId(queryByPlayerId: QueryByPlayerId): Player {
        val player = CompletableFuture<PlayerCommandHandler>()

        repository.load(queryByPlayerId.playerId).execute(player::complete)

        return player.get().player
    }
}
