package dev.tagliaferro.cqrs.player.plugins

import com.thoughtworks.xstream.XStream
import dev.tagliaferro.cqrs.player.handlers.PlayerCommandHandler
import dev.tagliaferro.cqrs.player.handlers.PlayerProjectionHandler
import org.axonframework.axonserver.connector.AxonServerConfiguration
import org.axonframework.axonserver.connector.AxonServerConnectionManager
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.messaging.MessageDispatchInterceptor
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.SimpleQueryBus
import org.axonframework.serialization.json.JacksonSerializer
import org.axonframework.serialization.xml.XStreamSerializer

object AxonConfiguration {
    private lateinit var axonConfiguration: Configuration

    fun getCommandGateway(): CommandGateway {
        if (!this::axonConfiguration.isInitialized) {
            start()
        }

        return axonConfiguration.commandGateway()
    }

    fun getQueryGateway(): QueryGateway {
        if (!this::axonConfiguration.isInitialized) {
            start()
        }

        return axonConfiguration.queryGateway()
    }

    fun start() {
        if (this::axonConfiguration.isInitialized) {
            return
        }

        val (commandBus, queryBus) = buildBus()
        val (axonServer, connectionManager) = configureServer()
        val (eventSerializer, snapshotSerializer) = buildSerializers()

        val axonServerEventStore = buildEventStore(
            axonServer,
            connectionManager,
            eventSerializer,
            snapshotSerializer
        )

        val configurer = DefaultConfigurer
            .defaultConfiguration()
            .configureAggregate(PlayerCommandHandler::class.java)
            .configureCommandBus { commandBus }
            .configureQueryBus { queryBus }
            .configureEventStore { axonServerEventStore }
            .registerQueryHandler { PlayerProjectionHandler::class.java }
            .buildConfiguration()

        connectionManager.start()
        configurer.start()

        axonConfiguration = configurer
    }

    private fun buildEventStore(
        axonServer: AxonServerConfiguration,
        connectionManager: AxonServerConnectionManager,
        eventSerializer: JacksonSerializer,
        snapshotSerializer: XStreamSerializer
    ): AxonServerEventStore {
        return AxonServerEventStore
            .builder()
            .configuration(axonServer)
            .platformConnectionManager(connectionManager)
            .eventSerializer(eventSerializer)
            .snapshotSerializer(snapshotSerializer)
            .build()
    }

    private fun buildSerializers(): Pair<JacksonSerializer, XStreamSerializer> {
        val basePackage = "dev.tagliaferro.cqrs.player.**"

        val eventSerializer = JacksonSerializer
            .builder()
            .build()

        val xStream = XStream()
        xStream.allowTypesByWildcard(arrayOf(basePackage))

        val snapshotSerializer = XStreamSerializer
            .builder()
            .xStream(xStream)
            .build()

        return Pair(eventSerializer, snapshotSerializer)
    }

    private fun buildBus(): Pair<SimpleCommandBus, SimpleQueryBus> {
        val commandBus = SimpleCommandBus
            .builder()
            .build()

        val queryBus = SimpleQueryBus
            .builder()
            .build()

        return Pair(commandBus, queryBus)
    }

    private fun configureServer(): Pair<AxonServerConfiguration, AxonServerConnectionManager> {
        val axonServer = AxonServerConfiguration
            .builder()
            .servers("localhost:8124")
            .build()

        val connectionManager = AxonServerConnectionManager
            .builder()
            .axonServerConfiguration(axonServer)
            .build()

        return Pair(axonServer, connectionManager)
    }
}
