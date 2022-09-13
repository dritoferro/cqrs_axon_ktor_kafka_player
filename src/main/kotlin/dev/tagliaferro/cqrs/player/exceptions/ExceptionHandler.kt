package dev.tagliaferro.cqrs.player.exceptions

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import java.util.concurrent.CompletionException
import java.util.concurrent.ExecutionException

private const val DEFAULT_ERROR_MESSAGE = "An internal error occurred"

fun StatusPagesConfig.configExceptionHandler() {
    exception<CompletionException> {call, exception ->
        when(exception.cause){
            is PlayerException -> {
                val errorMessage = exception.cause?.message ?: DEFAULT_ERROR_MESSAGE
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(errorMessage))
            }
        }
    }
}
