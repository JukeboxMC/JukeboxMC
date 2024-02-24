package org.jukeboxmc.server

import org.jukeboxmc.api.Whitelist
import org.jukeboxmc.api.config.Config
import org.jukeboxmc.api.config.ConfigType
import org.jukeboxmc.api.extensions.asType
import org.jukeboxmc.api.extensions.isType
import java.io.File

class JukeboxWhitelist : Whitelist {

    private var config: Config = Config(File(System.getProperty("user.dir"), "whitelist.json"), ConfigType.JSON)

    init {
        this.config.addDefault("whitelist", mutableListOf<String>())
        this.config.save()
    }

    override fun add(name: String) {
        if (!this.isWhitelisted(name) && this.config.exists("whitelist")) {
            if (this.config.get("whitelist")?.isType<MutableList<String>>() == true) {
                val list = this.config.get("whitelist")!!.asType<MutableList<String>>()
                list.add(name)
                this.config.set("whitelist", list)
                this.config.save()
            }
        }
    }

    override fun remove(name: String) {
        if (this.isWhitelisted(name) && this.config.exists("whitelist")) {
            if (this.config.get("whitelist")?.isType<MutableList<String>>() == true) {
                val list = this.config.get("whitelist")!!.asType<MutableList<String>>()
                list.removeIf { it == name }
                this.config.set("whitelist", list)
                this.config.save()
            }
        }
    }

    override fun isWhitelisted(name: String): Boolean {
        if (this.config.exists("whitelist")) {
            if (this.config.get("whitelist")?.isType<MutableList<String>>() == true) {
                return this.config.get("whitelist")!!.asType<MutableList<String>>().contains(name)
            }
        }
        return false
    }

    override fun getPlayers(): Collection<String> {
        if (this.config.exists("whitelist")) {
            if (this.config.get("whitelist")?.isType<MutableList<String>>() == true) {
                return this.config.get("whitelist")!!.asType<MutableList<String>>()
            }
        }
        return mutableListOf()
    }
}