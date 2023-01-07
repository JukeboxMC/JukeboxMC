package org.jukeboxmc.command.internal;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.player.Player;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name("enchant")
@Description("Adds an enchantment to a player's selected item.")
@Permission("jukeboxmc.command.enchant")
public class EnchantCommand extends Command {

    public EnchantCommand() {
        super( CommandData.builder()
                .setParameters( new CommandParameter[]{
                        new CommandParameter( "player", CommandParamType.TARGET, false ),
                        new CommandParameter( "enchantment", Stream.of( EnchantmentType.values() ).map( enchantmentType -> enchantmentType.name().toLowerCase() ).collect( Collectors.toList() ), false ),
                        new CommandParameter( "level", CommandParamType.INT, true ),
                } )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player player ) {
            if ( args.length >= 2 ) {
                Player target = Server.getInstance().getPlayer( args[0] );
                if ( target == null ) {
                    player.sendMessage( "§cThe player " + args[0] + " could not be found" );
                    return;
                }
                String enchantmentValue = args[1].toLowerCase();
                if ( !EnumUtils.isValidEnum( EnchantmentType.class, enchantmentValue.toUpperCase() ) ) {
                    commandSender.sendMessage( "§cEnchantment not found." );
                    return;
                }
                EnchantmentType enchantmentType = EnchantmentType.valueOf( enchantmentValue.toUpperCase() );

                int level = 1;
                if ( args.length >= 3 ) {
                    if ( !NumberUtils.isCreatable( args[2] ) ) {
                        commandSender.sendMessage( "§cThe level must be a number." );
                        return;
                    }
                    level = Integer.parseInt( args[2] );
                }
                Item itemInHand = player.getInventory().getItemInHand();
                if ( itemInHand.getType().equals( ItemType.AIR ) ) {
                    player.sendMessage( "§cAir can not be enchanted." );
                    return;
                }
                Enchantment enchantment = itemInHand.getEnchantment( enchantmentType );
                if ( enchantment != null ) {
                    player.sendMessage( "§cThe item already has the enchantment" );
                    return;
                }
                itemInHand.addEnchantment( enchantmentType, level );
                player.getInventory().setItemInHand( itemInHand );
                player.sendMessage( "Enchanting succeeded for " + target.getName() );
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player." );
        }
    }
}
