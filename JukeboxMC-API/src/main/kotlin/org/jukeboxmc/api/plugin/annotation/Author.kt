package org.jukeboxmc.api.plugin.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Author(val value: String)

