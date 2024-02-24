package org.jukeboxmc.server.command

import com.google.common.collect.ImmutableMap
import org.cloudburstmc.protocol.bedrock.data.command.*

class CommandParameter {
    var name: String
    var type: CommandParamType
    var optional: Boolean
    var enumData: CommandEnum? = null
    var postFix: String? = null

    private val PARAM_MAPPINGS = ImmutableMap.builder<CommandParamType, CommandParam>()
            .put(CommandParamType.INT, CommandParam.INT)
            .put(CommandParamType.FLOAT, CommandParam.FLOAT)
            .put(CommandParamType.VALUE, CommandParam.VALUE)
            .put(CommandParamType.WILDCARD_INT, CommandParam.WILDCARD_INT)
            .put(CommandParamType.OPERATOR, CommandParam.OPERATOR)
            .put(CommandParamType.TARGET, CommandParam.TARGET)
            .put(CommandParamType.WILDCARD_TARGET, CommandParam.WILDCARD_TARGET)
            .put(CommandParamType.FILE_PATH, CommandParam.FILE_PATH)
            .put(CommandParamType.INT_RANGE, CommandParam.INT_RANGE)
            .put(CommandParamType.STRING, CommandParam.STRING)
            .put(CommandParamType.POSITION, CommandParam.POSITION)
            .put(CommandParamType.BLOCK_POSITION, CommandParam.BLOCK_POSITION)
            .put(CommandParamType.MESSAGE, CommandParam.MESSAGE)
            .put(CommandParamType.TEXT, CommandParam.TEXT)
            .put(CommandParamType.JSON, CommandParam.JSON)
            .put(CommandParamType.COMMAND, CommandParam.COMMAND)
            .build()

    constructor(name: String, type: CommandParamType, optional: Boolean) {
        this.name = name
        this.type = type
        this.optional = optional
    }

    @JvmOverloads
    constructor(name: String, enumType: String, optional: Boolean = false) {
        this.name = name
        enumData = CommandEnum(enumType, arrayOf())
        type = CommandParamType.TEXT
        this.optional = optional
    }

    @JvmOverloads
    constructor(name: String, enumValues: MutableList<String>, optional: Boolean = false) {
        this.name = name
        type = CommandParamType.TEXT
        enumData = CommandEnum(name + "Enums", enumValues.toTypedArray())
        this.optional = optional
    }

    fun toNetwork(): CommandParamData {
        val commandParameter = CommandParamData()
        commandParameter.name = this.name
        commandParameter.isOptional = this.optional
        commandParameter.postfix = this.postFix
        commandParameter.enumData = if (this.enumData != null) {
            CommandEnumData(this.name, toNetwork(this.enumData!!.values), false)
        } else {
            null
        }
        commandParameter.type = PARAM_MAPPINGS[this.type]
        return commandParameter
    }

    fun toNetwork(values: Array<String>): LinkedHashMap<String, Set<CommandEnumConstraint>> {
        val map = LinkedHashMap<String, Set<CommandEnumConstraint>>()
        for (value in values) {
            map[value] = emptySet()
        }
        return map
    }
}

