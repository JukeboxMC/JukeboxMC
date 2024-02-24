package org.jukeboxmc.api


interface Whitelist {

    fun add(name: String)

    fun remove(name: String)

    fun isWhitelisted(name: String): Boolean

    fun getPlayers(): Collection<String>
}