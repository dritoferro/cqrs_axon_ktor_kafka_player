package team.cqrs.dev.tagliaferro.events

import java.util.UUID

data class PlayerContractedEvent(
    val playerId: UUID,
    val name: String
)
