package org.jukeboxmc.api.form.element

import com.google.gson.JsonObject

/**
 * @author Kaooot
 * @version 1.0
 */
class ImageButton(id: String, text: String, private val image: String) : Button(id, text) {

    override fun toJSON(): JsonObject {
        val button = super.toJSON()
        val jsonIcon = JsonObject()
        jsonIcon.addProperty("type", if (this.image.startsWith("http") || this.image.startsWith("https")) "url" else "path")
        jsonIcon.addProperty("data", this.image)
        button.add("image", jsonIcon)
        return button
    }
}