package org.jukeboxmc.api.form.element

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
abstract class Element(private val id: String, private val text: String) {

    open fun toJSON(): JsonObject {
        val element = JsonObject()
        element.addProperty("text", this.text)
        return element
    }

    open fun getAnswer(answerOption: Any): Any = answerOption

    fun getId() = this.id

    fun getText() = this.text
}