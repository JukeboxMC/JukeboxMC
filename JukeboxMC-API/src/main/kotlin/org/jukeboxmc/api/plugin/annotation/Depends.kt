package org.jukeboxmc.api.plugin.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Depends(vararg val value: String)

