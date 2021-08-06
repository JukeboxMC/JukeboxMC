package org.jukeboxmc.player.info;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class DeviceInfo {

    private String deviceName;
    private String deviceId;
    private long clientId;
    private Device device;
    private UIProfile UIProfile;

    public DeviceInfo( String deviceName, String deviceId, long clientId, Device device, UIProfile UIProfile ) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.clientId = clientId;
        this.device = device;
        this.UIProfile = UIProfile;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public long getClientId() {
        return this.clientId;
    }

    public Device getDevice() {
        return this.device;
    }

    public UIProfile getUIProfile() {
        return this.UIProfile;
    }
}
