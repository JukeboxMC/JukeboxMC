package org.jukeboxmc.api.form

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jukeboxmc.api.form.element.Button
import org.jukeboxmc.api.form.element.ImageButton

/**
 * @author Kaooot
 * @version 1.0
 */
class ButtonList(title: String) : Form<String>(title) {

    private val buttons = arrayListOf<Button>()
    private var content: String = ""

    override fun getFormType(): String = "form"

    override fun parseResponse(json: String): String? {
        val buttonId = json.trim().toInt()
        if (buttonId + 1 > this.buttons.size) {
            return null
        }
        return this.buttons[buttonId].getId()
    }

    override fun toJson(): JsonObject {
        if (this.cache != null && !this.dirty) {
            return this.cache!!
        }
        val jsonObject = super.toJson()
        val buttons = JsonArray()
        for (button in this.buttons) {
            buttons.add(button.toJSON())
        }
        jsonObject.addProperty("content", this.content)
        jsonObject.add("buttons", buttons)
        this.cache = jsonObject
        this.dirty = false
        return this.cache!!
    }

    fun setContent(content: String): ButtonList {
        this.content = content
        this.dirty = true
        return this
    }

    fun addButton(id: String, text: String): ButtonList {
        val button = Button(id, text)
        this.buttons.add(button)
        this.dirty = true
        return this
    }

    fun addImageButton(id: String, text: String, image: String): ButtonList {
        val imageButton = ImageButton(id, text, image)
        this.buttons.add(imageButton)
        this.dirty = true
        return this
    }
}