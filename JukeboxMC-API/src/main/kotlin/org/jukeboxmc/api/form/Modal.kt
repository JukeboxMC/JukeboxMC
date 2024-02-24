package org.jukeboxmc.api.form

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
class Modal(title: String, private val question: String) : Form<Boolean>(title) {

    private var trueButtonText: String? = null
    private var falseButtonText: String? = null

    override fun getFormType(): String = "modal"

    override fun parseResponse(json: String): Boolean = json.trim() == "true"

    override fun toJson(): JsonObject {
        if (this.cache != null && !this.dirty) {
            return this.cache!!
        }
        val jsonObject = super.toJson()
        jsonObject.addProperty("content", this.question)
        jsonObject.addProperty("button1", this.trueButtonText)
        jsonObject.addProperty("button2", this.falseButtonText)
        this.cache = jsonObject
        this.dirty = false
        return this.cache!!
    }

    fun setTrueButtonText(text: String) {
        this.trueButtonText = text
        this.setDirty()
    }

    fun setFalseButtonText(text: String) {
        this.falseButtonText = text
        this.setDirty()
    }
}