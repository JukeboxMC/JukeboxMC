package org.jukeboxmc.server

import org.jukeboxmc.api.Operators
import org.jukeboxmc.api.config.Config
import org.jukeboxmc.api.config.ConfigType
import org.jukeboxmc.api.extensions.asType
import org.jukeboxmc.api.extensions.isType
import java.io.File

class JukeboxOperators : Operators {

    private var config: Config = Config(File(System.getProperty("user.dir"), "operators.json"), ConfigType.JSON)

    init {
        this.config.addDefault("operators", mutableListOf<MutableMap<String, Any>>())
        this.config.save()
    }

    override fun add(name: String) {
        if (!this.isOperator(name) && this.config.exists("operators")) {
            if (this.config.get("operators")?.isType<MutableList<String>>() == true) {
                val list = this.config.get("operators")!!.asType<MutableList<String>>()
                list.add(name)
                this.config.set("operators", list)
                this.config.save()
            }
        }
    }

    override fun remove(name: String) {
        if (this.isOperator(name) && this.config.exists("operators")) {
            if (this.config.get("operators")?.isType<MutableList<String>>() == true) {
                val list = this.config.get("operators")!!.asType<MutableList<String>>()
                list.removeIf { it == name }
                this.config.set("operators", list)
                this.config.save()
            }
        }
    }

    override fun isOperator(name: String): Boolean {
        if (this.config.exists("operators")) {
            if (this.config.get("operators")?.isType<MutableList<String>>() == true) {
                return this.config.get("operators")!!.asType<MutableList<String>>().contains(name)
            }
        }
        return false
    }

    override fun getOperators(): Collection<String> {
        if (this.config.exists("operators")) {
            if (this.config.get("operators")?.isType<MutableList<String>>() == true) {
                return this.config.get("operators")!!.asType<MutableList<String>>()
            }
        }
        return mutableListOf()
    }

}