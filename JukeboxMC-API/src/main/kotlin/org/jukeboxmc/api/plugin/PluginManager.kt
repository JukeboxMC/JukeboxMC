package org.jukeboxmc.api.plugin

import org.jukeboxmc.api.Server
import org.jukeboxmc.api.event.Event
import org.jukeboxmc.api.event.Listener
import java.nio.file.Path

interface PluginManager {

    fun getPlugin(name: String): Plugin?

    fun getPlugins(): Collection<Plugin>

    fun loadPlugin(path: Path, directStartup: Boolean): Plugin?

    fun loadPlugin(path: Path): Plugin?

    fun disablePlugins()

    fun registerListener(listener: Listener)

    fun callEvent(event: Event)

    fun getServer(): Server
}