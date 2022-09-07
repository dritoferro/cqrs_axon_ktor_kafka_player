package dev.tagliaferro.cqrs.player.events

import dev.tagliaferro.cqrs.player.commands.ContractPlayerCommand
import java.util.UUID

data class PlayerContractedEvent(
    val playerId: UUID,
    val name: String,
    val age: Int,
    val nationality: String,
    val isRightHanded: Boolean,
    val isLeftHanded: Boolean
) {
    companion object {
        fun fromCommand(command: ContractPlayerCommand) = PlayerContractedEvent(
            playerId = command.id,
            name = command.name,
            age = command.age,
            nationality = command.nationality,
            isRightHanded = command.isRightHanded,
            isLeftHanded = command.isLeftHanded
        )
    }
}
