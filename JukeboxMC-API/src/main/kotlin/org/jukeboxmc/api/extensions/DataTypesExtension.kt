package org.jukeboxmc.api.extensions

inline fun <reified T> Any.isType(): Boolean {
    return (this is T)
}

inline fun <reified T> Any.asType(): T {
    return (this as T)
}