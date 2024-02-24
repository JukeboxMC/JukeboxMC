package org.jukeboxmc.api.player.info

/**
 * An enum representing all available devices and/or operating systems
 */
enum class Device(val id: Int) {

    UNKNOWN(-1),
    ANDROID(1),
    IOS(2),
    OSX(3),
    AMAZON(4),
    GEAR_VR(5),
    HOLOLENS(6),
    WINDOWS(7),
    WINDOWS_32(8),
    DEDICATED(9),
    TVOS(10),
    PLAYSTATION(11),
    NINTENDO(12),
    XBOX(13),
    WINDOWS_PHONE(14);

    companion object {
        fun getDevice(id: Int): Device? {
            return when (id) {
                1 -> ANDROID
                2 -> IOS
                3 -> OSX
                4 -> AMAZON
                5 -> GEAR_VR
                6 -> HOLOLENS
                7 -> WINDOWS
                8 -> WINDOWS_32
                9 -> DEDICATED
                10 -> TVOS
                11 -> PLAYSTATION
                12 -> NINTENDO
                13 -> XBOX
                14 -> WINDOWS_PHONE
                else -> null
            }
        }
    }
}