package jukeboxmc.network.packet.type;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Kaooot
 * @version 1.0
 */
@ToString
@Data
public class CommandOrigin {

    private byte unknown1;
    private UUID uuid;
    private byte unknown2;
    private byte type; // 0x01 = player, 0x03 = server
}