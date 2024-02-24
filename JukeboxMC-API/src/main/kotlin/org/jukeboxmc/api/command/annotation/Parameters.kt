package org.jukeboxmc.api.command.annotation

@Repeatable
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Parameters(vararg val parameter: Parameter)
