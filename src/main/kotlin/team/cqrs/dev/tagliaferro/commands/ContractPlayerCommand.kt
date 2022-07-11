package team.cqrs.dev.tagliaferro.commands

import java.util.UUID

data class ContractPlayerCommand(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val age: Int,
    val nationality: String,
    val isRightHanded: Boolean,
    val isLeftHanded: Boolean
)
