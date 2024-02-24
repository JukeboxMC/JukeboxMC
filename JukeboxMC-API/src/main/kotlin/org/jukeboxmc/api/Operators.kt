package org.jukeboxmc.api

interface Operators {

    fun add(name: String)

    fun remove(name: String)

    fun isOperator(name: String): Boolean

    fun getOperators(): Collection<String>
}