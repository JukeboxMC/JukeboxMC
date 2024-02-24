package org.jukeboxmc.api.player.info

/**
 * Device information gathered from a player during login process
 */
class DeviceInfo(
    private val deviceName: String,
    private val deviceId: String,
    private val clientId: Long,
    private val device: Device,
    private val uiProfile: UIProfile,
) {

    fun getDeviceName(): String {
        return this.deviceName
    }

    fun getDeviceId(): String {
        return this.deviceId
    }

    fun getClientId(): Long {
        return this.clientId
    }

    fun getDevice(): Device {
        return this.device
    }

    fun getUIProfile(): UIProfile {
        return this.uiProfile
    }

    override fun toString(): String {
        return "DeviceInfo(deviceName='$deviceName', deviceId='$deviceId', clientId=$clientId, device=$device, uiProfile=$uiProfile)"
    }
}