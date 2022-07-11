package team.cqrs.dev.tagliaferro.plugins

import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import team.cqrs.dev.tagliaferro.handlers.PlayerCommandHandler

fun commandGatewayBuilder(): DefaultCommandGateway {
    val commandBus = SimpleCommandBus
        .builder()
        .build()

    DefaultConfigurer
        .defaultConfiguration()
        .configureAggregate(PlayerCommandHandler::class.java)
        .configureEmbeddedEventStore { InMemoryEventStorageEngine() }
        .configureCommandBus { commandBus }
        .start()

    return DefaultCommandGateway
        .builder()
        .commandBus(commandBus)
        .build()
}
