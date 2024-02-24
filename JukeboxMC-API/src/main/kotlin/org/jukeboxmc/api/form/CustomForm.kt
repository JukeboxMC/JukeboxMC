package org.jukeboxmc.api.form

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jukeboxmc.api.form.element.*

/**
 * @author Kaooot
 * @version 1.0
 */
class CustomForm(title: String) : Form<FormResponse>(title) {

    private val elements = arrayListOf<Element>()

    override fun getFormType(): String = "custom_form"

    override fun parseResponse(json: String): FormResponse {
        val response = FormResponse()
        val answers = Gson().fromJson(json, JsonArray::class.java)
        for (i in 0..answers.size()) {
            val element = this.elements[i]
            response.addAnswer(element.getId(), element.getAnswer(answers[i]))
        }
        return response
    }

    override fun toJson(): JsonObject {
        val jsonObject = super.toJson()
        val content = jsonObject.getAsJsonArray("content")
        for (element in this.elements) {
            content.add(element.toJSON())
        }
        return jsonObject
    }

    fun createDropdown(id: String, text: String): Dropdown {
        val dropDown = Dropdown(this, id, text)
        this.elements.add(dropDown)
        this.setDirty()
        return dropDown
    }

    fun addInputField(id: String, text: String, placeHolder: String, defaultValue: String): CustomForm {
        val inputField = Input(id, text, placeHolder, defaultValue)
        this.elements.add(inputField)
        this.setDirty()
        return this
    }

    fun addLabel(text: String): CustomForm {
        val label = Label("", text)
        this.elements.add(label)
        this.setDirty()
        return this
    }

    fun addSlider(id: String, text: String, min: Float, max: Float, step: Float, defaultValue: Float): CustomForm {
        val slider = Slider(id, text, min, max, step, defaultValue)
        this.elements.add(slider)
        this.setDirty()
        return this
    }

    fun createStepSlider(id: String, text: String): StepSlider {
        val stepSlider = StepSlider(this, id, text)
        this.elements.add(stepSlider)
        this.setDirty()
        return stepSlider
    }

    fun addToggle(id: String, text: String, value: Boolean): CustomForm {
        val toggle = Toggle(id, text, value)
        this.elements.add(toggle)
        this.setDirty()
        return this
    }
}