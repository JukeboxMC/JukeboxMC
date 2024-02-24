package org.jukeboxmc.api.form.element

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
class Toggle(id: String, text: String, private var value: Boolean) : Element(id, text) {

    override fun toJSON(): JsonObject {
        val jsonObject = super.toJSON()
        jsonObject.addProperty("type", "toggle")
        jsonObject.addProperty("default", this.value)
        return jsonObject
    }

    override fun getAnswer(answerOption: Any): Any {
        val answer = super.getAnswer(answerOption) as Boolean
        this.value = answer
        return answer
    }
}