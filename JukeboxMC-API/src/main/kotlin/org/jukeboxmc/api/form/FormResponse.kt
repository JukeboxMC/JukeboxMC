package org.jukeboxmc.api.form

/**
 * @author Kaooot
 * @version 1.0
 */
class FormResponse {

    private val answers = hashMapOf<String, Any?>()

    fun addAnswer(id: String, data: Any?) {
        this.answers[id] = data
    }

    fun getToggle(id: String): Boolean? {
        val obj = this.answers[id]
        if (obj != null && obj is Boolean) {
            return obj
        }
        return null
    }

    fun getStepSlider(id: String): String? {
        val obj = this.answers[id]
        if (obj != null && obj is String) {
            return obj
        }
        return null
    }

    fun getSlider(id: String): Float? {
        val obj = this.answers[id]
        if (obj != null && obj is Double) {
            return obj.toFloat()
        }
        return null
    }

    fun getInput(id: String): String? {
        val obj = this.answers[id]
        if (obj != null && obj is String) {
            return obj
        }
        return null
    }

    fun getDropbox(id: String): String? {
        val obj = this.answers[id]
        if (obj != null && obj is String) {
            return obj
        }
        return null
    }
}