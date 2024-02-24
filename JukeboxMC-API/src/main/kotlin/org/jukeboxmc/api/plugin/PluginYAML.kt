package org.jukeboxmc.api.plugin

class PluginYAML(
    var name: String? = null,
    var version: String? = null,
    var author: String? = null,
    var main: String? = null,
    var loadOrder: PluginLoadOrder = PluginLoadOrder.STARTUP,
    var authors: MutableList<String> = mutableListOf(),
    var depends: MutableList<String> = mutableListOf(),
) {

    override fun toString(): String {
        return "PluginYAML(name='$name', version='$version', author='$author', main='$main', loadOrder=$loadOrder, authors=$authors, depends=$depends)"
    }
}