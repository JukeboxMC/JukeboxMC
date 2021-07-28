package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Validator {

    private final List<String> values;

    public Validator() {
        this.values = new ArrayList<>();
    }

    public List<String> getValues() {
        return this.values;
    }

    public void addValue( String value ) {
        this.values.add( value );
    }

    public String getName() {
       return "value";
    }

    public CommandParamType getCommandParamType() {
        return CommandParamType.RAWTEXT;
    }

    public String parseIterator( Iterator<String> data ) {
        return null;
    }

    public abstract Object parseObject( String value );
}
