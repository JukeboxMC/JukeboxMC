package org.jukeboxmc.api.form.element

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jukeboxmc.api.form.CustomForm

/**
 * @author Kaooot
 * @version 1.0
 */
class StepSlider(private val form: CustomForm, id: String, text: String) : Element(id, text) {

    private val steps = arrayListOf<String>()
    private var defaultStep = 0

    override fun toJSON(): JsonObject {
        val jsonObject = super.toJSON()
        jsonObject.addProperty("type", "step_slider")
        val steps = JsonArray()
        for (step in this.steps) {
            steps.add(step)
        }
        jsonObject.add("steps", steps)
        jsonObject.addProperty("default", this.defaultStep)
        return jsonObject
    }

    override fun getAnswer(answerOption: Any): Any {
        val answer = answerOption as String
        this.defaultStep = this.steps.indexOf(answer)
        return answer
    }

    fun addStep(step: String): StepSlider {
        this.steps.add(step)
        this.form.setDirty()
        return this
    }

    fun addStep(step: String, defaultStep: Boolean): StepSlider {
        if (defaultStep) {
            this.defaultStep = this.steps.size
        }
        this.steps.add(step)
        this.form.setDirty()
        return this
    }
}