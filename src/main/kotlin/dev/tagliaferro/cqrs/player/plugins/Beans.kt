package dev.tagliaferro.cqrs.player.plugins

import dev.tagliaferro.cqrs.player.api.PlayerController
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.Dispatchers

val playerController = PlayerController(
    AxonConfiguration.getCommandGateway(),
    AxonConfiguration.getQueryGateway(),
    Dispatchers.IO
)

object Envs {
    private lateinit var envs: Dotenv

    fun get(key: String, default: String?): String {
        if (!this::envs.isInitialized) {
            envs = dotenv()
        }

        return envs[key, default]
    }
}
