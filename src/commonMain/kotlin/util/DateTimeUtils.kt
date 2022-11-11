package util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

fun Duration.time(): String = this.toComponents { hours, minutes, seconds, _ ->
    return (if (hours > 0) "%02d:".format(hours) else "") + "%02d:%02d".format(minutes, seconds)
}

fun Instant.date(): String = this.toLocalDateTime(TimeZone.of("Europe/Moscow")).date.let {
    "${it.year}.${it.monthNumber}.${it.dayOfMonth}"
}