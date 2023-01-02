package org.jukeboxmc.util;

import lombok.Getter;

@Getter
public class Identifier {

    private static final String DEFAULT_NAMESPACE = "minecraft";
    private static final char SEPARATOR = ':';

    private static final Identifier EMPTY = new Identifier("", "", String.valueOf(SEPARATOR));

    private final String namespace;
    private final String name;
    private final String fullName;
    private final int hashCode;

    public Identifier( String namespace, String name, String fullName) {
        this.namespace = namespace;
        this.name = name;
        this.fullName = fullName;
        this.hashCode = fullName.hashCode();
    }

    public static Identifier fromString(String str) {
        if(str.isBlank()) return EMPTY;
        str = str.trim();
        final String[] nameParts = str.indexOf(SEPARATOR) != -1 ? str.split(String.valueOf(SEPARATOR)) : new String[]{str};
        final String namespace = nameParts.length > 1 ? nameParts[0] : DEFAULT_NAMESPACE;
        final String name = nameParts.length > 1 ? nameParts[1] : str;
        final String fullName = namespace + SEPARATOR + name;
        return new Identifier(namespace, name, fullName);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Identifier && ((Identifier) o).hashCode == this.hashCode;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

}
