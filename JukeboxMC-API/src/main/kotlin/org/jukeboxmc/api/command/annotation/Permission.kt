package org.jukeboxmc.api.command.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(val value: String = "", val permissionMessage: Boolean = true)
