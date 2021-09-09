package jukeboxmc.network.packet.type;

import lombok.Data;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;

import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
public class TablistEntry {

    private UUID uuid;
    private long entityId = 0;
    private String name;
    private DeviceInfo deviceInfo;
    private String xboxId;
    private Skin skin;

    public TablistEntry( UUID uuid ) {
        this.uuid = uuid;
    }

    public TablistEntry( UUID uuid, long entityId, String name, DeviceInfo deviceInfo, String xboxId, Skin skin ) {
        this.uuid = uuid;
        this.entityId = entityId;
        this.name = name;
        this.deviceInfo = deviceInfo;
        this.xboxId = xboxId;
        this.skin = skin;
    }
}
