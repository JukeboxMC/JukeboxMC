package org.jukeboxmc.api

class Identifier private constructor(
    private val namespace: String,
    private val name: String,
    private val fullName: String,
    private val hashCodeValue: Int = fullName.hashCode()
) {

    companion object {
        private const val DEFAULT_NAMESPACE = "minecraft"
        private const val SEPARATOR = ':'

        val EMPTY: Identifier = Identifier("", "", SEPARATOR.toString(), SEPARATOR.toString().hashCode())

        fun fromString(str: String): Identifier {
            if (str.isBlank()) EMPTY
            var strValue = str
            strValue = strValue.trim { it <= ' ' }
            val nameParts = if (strValue.indexOf(SEPARATOR) != -1) strValue.split(SEPARATOR.toString().toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() else arrayOf(strValue)
            val namespace = if (nameParts.size > 1) nameParts[0] else DEFAULT_NAMESPACE
            val name = if (nameParts.size > 1) nameParts[1] else strValue
            val fullName: String = namespace + SEPARATOR + name
            return Identifier(namespace, name, fullName)
        }
    }

    fun getNamespace(): String {
        return this.namespace
    }

    fun getName(): String {
        return this.name
    }

    fun getFullName(): String {
        return this.fullName
    }

    override fun equals(other: Any?): Boolean {
        return other is Identifier && other.hashCodeValue == this.hashCodeValue
    }

    override fun hashCode(): Int {
        return hashCodeValue
    }

    override fun toString(): String {
        return fullName
    }
}