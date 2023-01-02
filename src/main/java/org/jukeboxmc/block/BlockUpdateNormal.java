package org.jukeboxmc.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.block.direction.BlockFace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class BlockUpdateNormal {

    private Block block;
    private BlockFace blockFace;

}
