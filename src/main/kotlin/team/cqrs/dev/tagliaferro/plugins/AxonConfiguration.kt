package team.cqrs.dev.tagliaferro.plugins

import org.axonframework.axonserver.connector.AxonServerConfiguration
import org.axonframework.axonserver.connector.AxonServerConnectionManager
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.DefaultConfigurer
import org.axonframework.queryhandling.SimpleQueryBus
import team.cqrs.dev.tagliaferro.handlers.PlayerCommandHandler

fun commandGatewayBuilder(): CommandGateway {
    val commandBus = SimpleCommandBus
        .builder()
        .build()

    val queryBus = SimpleQueryBus.builder().build()

    val axonServer = AxonServerConfiguration
        .builder()
        .servers("localhost:8124")
        .build()

    val connectionManager = AxonServerConnectionManager
        .builder()
        .axonServerConfiguration(axonServer)
        .build()

    val axonServerEventStore = AxonServerEventStore
        .builder()
        .configuration(axonServer)
        .platformConnectionManager(connectionManager)
        .build()

    val configurer = DefaultConfigurer
        .defaultConfiguration()
        .configureAggregate(PlayerCommandHandler::class.java)
        .configureCommandBus { commandBus }
        .configureQueryBus { queryBus }
        .configureEventStore { axonServerEventStore }
        .buildConfiguration()

    connectionManager.start()
    configurer.start()

    return configurer.commandGateway()
}
