package org.jukeboxmc.api.form

import com.google.gson.JsonArray
import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
abstract class Form<R>(private val title: String, private var icon: String? = null) {

    protected var cache: JsonObject? = null
    protected var dirty: Boolean = false

    abstract fun getFormType(): String

    abstract fun parseResponse(json: String) : R?

    fun setIcon(icon: String) {
        this.icon = icon
        this.dirty = true
    }

    fun getIcon() = this.icon

    fun getTitle() = this.title

    open fun toJson(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("type", this.getFormType())
        jsonObject.addProperty("title", this.getTitle())
        jsonObject.add("content", JsonArray())

        if (this.icon != null) {
            val icon = JsonObject()
            icon.addProperty("type", if (this.icon!!.startsWith("http") || this.icon!!.startsWith("https")) "url" else "path")
            icon.addProperty("data", this.icon!!)
            jsonObject.add("icon", icon)
        }
        return jsonObject
    }

    fun setDirty() {
        this.dirty = true
    }
}