package team.cqrs.dev.tagliaferro.domain

import team.cqrs.dev.tagliaferro.commands.ContractPlayerCommand
import java.util.UUID

data class Player(
    val id: UUID,
    val name: String,
    val age: Int,
    val nationality: String,
    val isRightHanded: Boolean,
    val isLeftHanded: Boolean
) {
    companion object {
        fun fromCommand(command: ContractPlayerCommand) = Player(
            id = command.id,
            name = command.name,
            age = command.age,
            nationality = command.nationality,
            isRightHanded = command.isRightHanded,
            isLeftHanded = command.isLeftHanded
        )
    }
}
