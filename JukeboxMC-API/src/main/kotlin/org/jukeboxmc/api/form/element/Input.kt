package org.jukeboxmc.api.form.element

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
class Input(id: String, text: String, private val placeHolder: String, private var defaultValue: String) : Element(id, text) {

    override fun toJSON(): JsonObject {
        val jsonObject = super.toJSON()
        jsonObject.addProperty("type", "input")
        jsonObject.addProperty("placeholder", this.placeHolder)
        jsonObject.addProperty("default", this.defaultValue)
        return jsonObject
    }

    override fun getAnswer(answerOption: Any): Any {
        val answer = super.getAnswer(answerOption) as String
        this.defaultValue = answer
        return answer
    }
}