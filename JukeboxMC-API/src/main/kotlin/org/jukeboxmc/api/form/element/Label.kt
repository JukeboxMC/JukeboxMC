package org.jukeboxmc.api.form.element

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
class Label(id: String, text: String) : Element(id,text) {

    override fun toJSON(): JsonObject {
        val jsonObject = super.toJSON()
        jsonObject.addProperty("type", "label")
        return jsonObject
    }
}