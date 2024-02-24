package org.jukeboxmc.api.plugin.annotation

import org.jukeboxmc.api.plugin.PluginLoadOrder

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Startup(val value: PluginLoadOrder)

