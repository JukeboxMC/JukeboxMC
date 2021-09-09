package jukeboxmc.player.info;

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
    private GUIScale guiScale;

    public DeviceInfo( String deviceName, String deviceId, long clientId, Device device, GUIScale guiScale ) {
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.clientId = clientId;
        this.device = device;
        this.guiScale = guiScale;
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

    public GUIScale getGuiScale() {
        return this.guiScale;
    }
}
