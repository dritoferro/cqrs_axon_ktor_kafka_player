package dev.tagliaferro.cqrs.player.plugins

import dev.tagliaferro.cqrs.player.api.PlayerController
import dev.tagliaferro.cqrs.player.exceptions.PlayerException
import kotlinx.coroutines.Dispatchers

val playerController = PlayerController(
    AxonConfiguration.getCommandGateway(),
    AxonConfiguration.getQueryGateway(),
    Dispatchers.IO
)

object Envs {
    fun get(key: String, default: String?): String {
        return System.getenv(key) ?: default ?: throw PlayerException("Environment not found for key: $key")
    }
}
