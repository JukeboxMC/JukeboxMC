package org.jukeboxmc.api.form

import java.util.function.Consumer

/**
 * @author Kaooot
 * @version 1.0
 */
class FormListener<R> {

    private var responseConsumer: Consumer<Any> = Consumer { }
    private var closeConsumer: Consumer<Void?> = Consumer { }

    fun onResponse(consumer: Consumer<Any>): FormListener<R> {
        this.responseConsumer = consumer
        return this
    }

    fun onClose(consumer: Consumer<Void?>): FormListener<R> {
        this.closeConsumer = consumer
        return this
    }

    fun getResponseConsumer() = this.responseConsumer

    fun getCloseConsumer() = this.closeConsumer
}