package org.jukeboxmc.api.world.gamerule

class GameRuleValue(var identifier: String, var value: Any) {
    override fun toString(): String {
        return "GameRuleValue(name='$identifier', value=$value)"
    }
}