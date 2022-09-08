package dev.tagliaferro.cqrs.player.domain

import dev.tagliaferro.cqrs.player.events.PlayerContractedEvent
import java.util.UUID

data class Player(
    val playerId: UUID,
    val name: String,
    val age: Int,
    val nationality: String,
    val isRightHanded: Boolean,
    val isLeftHanded: Boolean,
    val teamId: UUID
) {
    companion object {
        fun fromEvent(event: PlayerContractedEvent) = Player(
            playerId = event.playerId,
            name = event.name,
            age = event.age,
            nationality = event.nationality,
            isRightHanded = event.isRightHanded,
            isLeftHanded = event.isLeftHanded,
            teamId = event.teamId
        )
    }
}
