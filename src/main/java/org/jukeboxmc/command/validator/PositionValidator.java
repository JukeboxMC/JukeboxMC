package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.Iterator;

/**
 * @author LucGamesYT, GoMint
 * @version 1.0
 */
public class PositionValidator extends Validator {

    @Override
    public String parseIterator( Iterator<String> data ) {
        StringBuilder builder = new StringBuilder();
        for ( int i = 0; i < 3; i++ ) {
            if ( !data.hasNext() ) {
                return null;
            }
            builder.append( data.next() ).append( " " );
        }
        return builder.deleteCharAt( builder.length() - 1 ).toString();
    }

    @Override
    public Object parseObject( String value, CommandSender commandSender ) {
        Vector entityPosition = new Vector( 0, 0, 0 );
        if ( commandSender instanceof Player ) {
            // Mojang decided that ~ is the current entity position
            entityPosition = ( (Player) commandSender ).getLocation();
        }

        // Split string
        String[] split = value.split( " " );

        // Parse x
        Integer xInt = this.parsePos( entityPosition.getBlockX(), split[0] );
        if ( xInt == null ) {
            return null;
        }

        Integer yInt = this.parsePos( entityPosition.getBlockY(), split[1] );
        if ( yInt == null ) {
            return null;
        }

        Integer zInt = this.parsePos( entityPosition.getBlockZ(), split[2] );
        if ( zInt == null ) {
            return null;
        }

        return new Vector( xInt, yInt, zInt );
    }

    @Override
    public String getName() {
        return "position: x y z";
    }

    @Override
    public CommandParamType getCommandParamType() {
        return CommandParamType.BLOCK_POS;
    }

    private Integer parsePos( int positionValue, String in ) {
        if ( in.startsWith( "~" ) && positionValue != 0 ) {
            // Do we have additional data (+/-)?
            if ( in.length() > 2 ) {
                if ( in.startsWith( "~+" ) ) {
                    try {
                        int diffX = Integer.parseInt( in.substring( 2 ) );
                        positionValue += diffX;
                        return positionValue;
                    } catch ( NumberFormatException e ) {
                        return null;
                    }
                } else if ( in.startsWith( "~-" ) ) {
                    try {
                        int diffX = Integer.parseInt( in.substring( 2 ) );
                        positionValue -= diffX;
                        return positionValue;
                    } catch ( NumberFormatException e ) {
                        return null;
                    }
                }
            } else {
                try {
                    int diffX = Integer.parseInt( in.substring( 1 ) );
                    positionValue += diffX;
                    return positionValue;
                } catch ( NumberFormatException e ) {
                    return null;
                }
            }
        } else {
            try {
                return Integer.parseInt( in );
            } catch ( NumberFormatException e ) {
                return null;
            }
        }

        return null;
    }
}
