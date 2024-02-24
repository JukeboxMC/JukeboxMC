package org.jukeboxmc.api.command.annotation

import org.jukeboxmc.api.command.ParameterType

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class Parameter(val name: String, val parameterType: ParameterType = ParameterType.UNKNOWN, vararg val enumValues: String = [], val optional: Boolean = false)
