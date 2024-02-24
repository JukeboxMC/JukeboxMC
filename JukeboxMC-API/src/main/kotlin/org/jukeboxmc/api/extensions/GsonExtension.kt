package org.jukeboxmc.api.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader


inline fun <reified T> gsonTypeRef(): TypeToken<T> = object : TypeToken<T>() {}

inline fun <reified T> Gson.fromJson(reader: Reader): T = fromJson(reader, gsonTypeRef<T>())