package org.jukeboxmc.api.form.element

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jukeboxmc.api.form.CustomForm

/**
 * @author Kaooot
 * @version 1.0
 */
class Dropdown(private val form: CustomForm, id: String, text: String) : Element(id, text) {

    private val options = arrayListOf<String>()
    private var defaultOption = 0

    override fun toJSON(): JsonObject {
        val jsonObject = super.toJSON()
        jsonObject.addProperty("type", "dropdown")
        val options = JsonArray()
        for (option in this.options) {
            options.add(option)
        }
        jsonObject.add("options", options)
        jsonObject.addProperty("default", this.defaultOption)
        return jsonObject
    }

    override fun getAnswer(answerOption: Any): Any {
        val optionIndex = answerOption as Long
        this.defaultOption = optionIndex.toInt()
        return this.options[optionIndex.toInt()]
    }

    fun addOption(option: String): Dropdown {
        this.options.add(option)
        this.form.setDirty()
        return this
    }

    fun addOption(option: String, defaultOption: Boolean): Dropdown {
        if (defaultOption) {
            this.defaultOption = this.options.size
        }
        this.options.add(option)
        this.form.setDirty()
        return this
    }
}