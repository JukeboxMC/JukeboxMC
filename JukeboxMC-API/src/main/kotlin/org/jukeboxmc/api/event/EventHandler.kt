package org.jukeboxmc.api.event


@Target(AnnotationTarget.FUNCTION)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class EventHandler(val priority: EventPriority = EventPriority.NORMAL)