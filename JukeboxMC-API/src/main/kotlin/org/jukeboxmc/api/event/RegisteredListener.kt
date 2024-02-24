package org.jukeboxmc.api.event

import java.lang.reflect.Method

class RegisteredListener(
    val methode: Method,
    val listener: Listener,
)