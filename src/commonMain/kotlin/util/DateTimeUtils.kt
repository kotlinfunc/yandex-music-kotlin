package util

import kotlin.time.Duration

fun Duration.time(): String = this.toComponents { hours, minutes, seconds, _ ->
    return (if (hours > 0) "%02d:".format(hours) else "") + "%02d:%02d".format(minutes, seconds)
}