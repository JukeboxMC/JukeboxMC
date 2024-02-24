package org.jukeboxmc.api.form.element

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
class Slider(id: String, text: String, private val min: Float, private val max: Float, private val step: Float, private var defaultValue: Float) : Element(id, text) {

    override fun toJSON(): JsonObject {
        val jsonObject = super.toJSON()
        jsonObject.addProperty("type", "slider")
        jsonObject.addProperty("min", this.min)
        jsonObject.addProperty("max", this.max)
        if (this.step > 0) {
            jsonObject.addProperty("step", this.step)
        }
        if (this.defaultValue > this.min) {
            jsonObject.addProperty("default", this.defaultValue)
        }
        return jsonObject
    }

    override fun getAnswer(answerOption: Any): Any {
        val answer = super.getAnswer(answerOption) as Double
        this.defaultValue = answer.toFloat()
        return answer
    }
}